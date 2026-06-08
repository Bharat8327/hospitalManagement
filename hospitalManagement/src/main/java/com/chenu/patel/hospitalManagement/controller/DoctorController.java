package com.chenu.patel.hospitalManagement.controller;

import com.chenu.patel.hospitalManagement.dto.AppointmentResponseDto;
import com.chenu.patel.hospitalManagement.entity.Appointment;
import com.chenu.patel.hospitalManagement.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final AppointmentService appointmentService;

    @GetMapping("/{doctorId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAllApointmentOfDoctor(@PathVariable Long doctorId){
        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(doctorId));
    }
}
