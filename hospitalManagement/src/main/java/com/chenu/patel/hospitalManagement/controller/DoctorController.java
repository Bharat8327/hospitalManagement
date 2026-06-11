package com.chenu.patel.hospitalManagement.controller;

import com.chenu.patel.hospitalManagement.dto.AppointmentResponseDto;
import com.chenu.patel.hospitalManagement.entity.User;
import com.chenu.patel.hospitalManagement.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final AppointmentService appointmentService;

    @GetMapping("/{doctorId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAllApointmentOfDoctor(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(user.getId()));
    }
}
