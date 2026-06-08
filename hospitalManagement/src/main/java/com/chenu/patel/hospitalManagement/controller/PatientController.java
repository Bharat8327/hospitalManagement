package com.chenu.patel.hospitalManagement.controller;

import com.chenu.patel.hospitalManagement.dto.AppointmentResponseDto;
import com.chenu.patel.hospitalManagement.dto.CreateAppointmentRequestDto;
import com.chenu.patel.hospitalManagement.dto.PatientResponseDto;
import com.chenu.patel.hospitalManagement.service.AppointmentService;
import com.chenu.patel.hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/profile/{patientId}")
    private ResponseEntity<PatientResponseDto> getPatientProfile(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

}
