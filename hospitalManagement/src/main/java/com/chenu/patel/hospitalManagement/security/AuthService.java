package com.chenu.patel.hospitalManagement.security;

import com.chenu.patel.hospitalManagement.dto.LoginRequestDto;
import com.chenu.patel.hospitalManagement.dto.LoginResponseDto;
import com.chenu.patel.hospitalManagement.dto.SignupRequestDto;
import com.chenu.patel.hospitalManagement.entity.Patient;
import com.chenu.patel.hospitalManagement.entity.User;
import com.chenu.patel.hospitalManagement.entity.type.AuthProviderType;
import com.chenu.patel.hospitalManagement.entity.type.RoleType;
import com.chenu.patel.hospitalManagement.repository.PatientRepository;
import com.chenu.patel.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );
        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateJwtToken(user);

        return new LoginResponseDto(token,user.getId());
    }




    public User signup(SignupRequestDto signup ,AuthProviderType authProviderType ,String  providerId){
        User user = userRepository.findByUsername(signup.getUsername()).orElse(null);

        if(user!=null) {
            throw new RuntimeException("Username already exists");
        }
        user =  User.builder()
                .username(signup.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .roles(signup.getRole()) // Set.of(RoleType.PATIENT)
                .build();

        if(authProviderType  == AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signup.getPassword()));
        }
        user = userRepository.save(user);

        Patient patient = Patient.builder()
                .email(signup.getEmail())
                .name(signup.getUsername())
                .user(user)
                .build();

        patientRepository.save(patient);


        return user;

    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registertionId) {
        // fetch provider and providerId
        AuthProviderType providerType = authUtil.getAuthProviderTypeAndRegistractionrId(registertionId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User,registertionId);

        User user = userRepository.findByProviderIdAndProviderType(providerId,providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(emailUser==null&&user==null){
            // sign up flow
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User,registertionId,providerId);
           user =  signup(new SignupRequestDto(username,null,name,Set.of(RoleType.PATIENT)),providerType,providerId);
        }else if(user!=null){
            if(email!=null&&!email.isBlank()&&!email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            throw new BadCredentialsException("This is already registered with provider "+emailUser.getProviderType());
        }


        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateJwtToken(user),user.getId());
        return ResponseEntity.ok(loginResponseDto);

        //save the provider type and provider id info with user
        // if the user have an account directly login
        // otherwise , first signup and then login
    }
}
