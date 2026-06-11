package com.chenu.patel.hospitalManagement.controller;

import com.chenu.patel.hospitalManagement.dto.DoctorResponseDto;
import com.chenu.patel.hospitalManagement.dto.OnBoardDoctorRequestDto;
import com.chenu.patel.hospitalManagement.dto.PatientResponseDto;
import com.chenu.patel.hospitalManagement.entity.Patient;
import com.chenu.patel.hospitalManagement.service.DoctorService;
import com.chenu.patel.hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                                   @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDto>onBoardNewDoctor(@RequestBody OnBoardDoctorRequestDto onBoardDoctorRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onBoardDoctorRequest));
    }
}
