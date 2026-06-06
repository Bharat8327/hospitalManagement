package com.chenu.patel.hospitalManagement.service;

import com.chenu.patel.hospitalManagement.entity.Appointment;
import com.chenu.patel.hospitalManagement.entity.Doctor;
import com.chenu.patel.hospitalManagement.entity.Patient;
import com.chenu.patel.hospitalManagement.repository.AppointmentRepository;
import com.chenu.patel.hospitalManagement.repository.DoctorRepository;
import com.chenu.patel.hospitalManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Appointment createAppointment(Appointment appointment , Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId).get(); // findById return optional then apply get() methode
        Patient patient = patientRepository.findById(patientId).get();

        if(appointment.getId()!=null) {
             throw new IllegalArgumentException("Appointment should not have an id");
        }
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        patient.getAppointments().add(appointment); // to maintain bidirectional

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment reAssignmentAppointment(Long doctorId, Long appointmentId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();

        appointment.setDoctor(doctor); // these will be automatically call the update , because it is dirty

        doctor.getAppointments().add(appointment); // just for bidirectonal consistency
            return appointment;

    }

}
