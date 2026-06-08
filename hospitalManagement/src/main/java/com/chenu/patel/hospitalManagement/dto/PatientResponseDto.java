package com.chenu.patel.hospitalManagement.dto;

import com.chenu.patel.hospitalManagement.entity.type.BloodGroup;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGroup bloodGroup;
}
