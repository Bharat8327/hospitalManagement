package com.chenu.patel.hospitalManagement.security;

import com.chenu.patel.hospitalManagement.dto.LoginRequestDto;
import com.chenu.patel.hospitalManagement.dto.LoginResponseDto;
import com.chenu.patel.hospitalManagement.dto.SignupRequestDto;
import com.chenu.patel.hospitalManagement.entity.User;
import com.chenu.patel.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );
        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateJwtToken(user);

        return new LoginResponseDto(token,user.getId());
    }


    public void signup(SignupRequestDto signup){
        User user = userRepository.findByUsername(signup.getUsername()).orElse(null);
        if(user!=null)throw new RuntimeException("Username already exists");

         userRepository.save(user.builder().username(signup.getUsername()).password(passwordEncoder.encode(signup.getPassword())).build());
    }
}
