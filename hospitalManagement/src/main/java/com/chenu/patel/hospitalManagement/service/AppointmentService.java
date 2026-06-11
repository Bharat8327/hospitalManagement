package com.chenu.patel.hospitalManagement.service;

import com.chenu.patel.hospitalManagement.dto.AppointmentResponseDto;
import com.chenu.patel.hospitalManagement.dto.CreateAppointmentRequestDto;
import com.chenu.patel.hospitalManagement.entity.Appointment;
import com.chenu.patel.hospitalManagement.entity.Doctor;
import com.chenu.patel.hospitalManagement.entity.Patient;
import com.chenu.patel.hospitalManagement.repository.AppointmentRepository;
import com.chenu.patel.hospitalManagement.repository.DoctorRepository;
import com.chenu.patel.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Secured("ROLE_PATIENT")
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto appointment) {
        Long doctorId = appointment.getDoctorId();
        Long patientId = appointment.getPatientId();

        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + doctorId));

        Appointment appointment1 = Appointment.builder()
                .reason(appointment.getReason())
                .appointmentTime(LocalDateTime.parse(appointment.getAppointmentTime()))
                .build();

        appointment1.setDoctor(doctor);
        appointment1.setPatient(patient);
        Appointment save = appointmentRepository.save(appointment1);
        return modelMapper.map(save,AppointmentResponseDto.class);
    }

    @Transactional
    @PreAuthorize("hasAuthority('appointment:write') OR #DoctorId == authentication.principal.getId() ") // called spel expression
    public Appointment reAssignmentAppointment(Long doctorId, Long appointmentId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();

        appointment.setDoctor(doctor); // these will be automatically call the update , because it is dirty

        doctor.getAppointments().add(appointment); // just for bidirectonal consistency
            return appointment;

    }


    @Transactional
    @PreAuthorize("hashRole('ADMIN') OR hashRole('DOCTOR') AND #DoctorId == authentication.principal.getId() ") // called spel expression
     public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long DoctorId){
        Doctor doctor = doctorRepository.findById(DoctorId).orElseThrow(()->new EntityNotFoundException("Doctor not found with id " + DoctorId));
          return doctor.getAppointments()
                .stream()
                .map(a -> new AppointmentResponseDto(
                        a.getId(),
                        a.getAppointmentTime(),
                        a.getReason(),
                        a.getPatient().getName()
                ))
                .toList();
    }

}
