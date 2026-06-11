package com.chenu.patel.hospitalManagement.service;

import com.chenu.patel.hospitalManagement.dto.DoctorResponseDto;
import com.chenu.patel.hospitalManagement.dto.OnBoardDoctorRequestDto;
import com.chenu.patel.hospitalManagement.entity.Doctor;
import com.chenu.patel.hospitalManagement.entity.User;
import com.chenu.patel.hospitalManagement.entity.type.RoleType;
import com.chenu.patel.hospitalManagement.repository.DoctorRepository;
import com.chenu.patel.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Secured("ROLE_ADMIN")
    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("hasAuthority('appointment:write') or #onBoardDoctorRequest.getUserId() == authentication.principal.getId()")
    public DoctorResponseDto onBoardNewDoctor(OnBoardDoctorRequestDto onBoardDoctorRequest) {
        User user = userRepository.findById(onBoardDoctorRequest.getUserId()).orElseThrow();
        if(doctorRepository.existsById(onBoardDoctorRequest.getUserId())){
            throw new IllegalArgumentException("Already a doctor");
        }

        Doctor doctor = Doctor.builder()
                .name(onBoardDoctorRequest.getName())
                .specialization(onBoardDoctorRequest.getSpecialization())
                .user(user)
                .build();
        user.getRoles().add(RoleType.DOCTOR);

      return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDto.class);
    }
}
