package com.chenu.patel.hospitalManagement;


import com.chenu.patel.hospitalManagement.entity.Appointment;
import com.chenu.patel.hospitalManagement.entity.Insurance;
import com.chenu.patel.hospitalManagement.entity.Patient;
import com.chenu.patel.hospitalManagement.repository.PatientRepository;
import com.chenu.patel.hospitalManagement.service.AppointmentService;
import com.chenu.patel.hospitalManagement.service.InsuranceService;
import com.chenu.patel.hospitalManagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PatientTest {

    @Autowired
    private  InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    void assignPatientInsuraceTest(){
        Insurance insur = Insurance.builder()
                .policyNumber("HDFC_12393")
                .proivder("HDFC")
                .validUntil(LocalDate.of(2030,02,12))
                .build();
        Patient pat = insuranceService.assignInsurance(insur,1L);
        System.out.println(pat);

        Patient pat2 = insuranceService.disaccociateInsuranceFromPatient(pat.getId());
        System.out.println(pat2);
    }


    @Test
    void createAppointmentTest(){
        Appointment appointment1 = Appointment.builder()
                        .reason("health issue")
                .appointmentTime(LocalDateTime.of(2026 , 06 ,7,12,33,00))
                .build();
        Appointment appointment2 = Appointment.builder()
                .reason("headache")
                .appointmentTime(LocalDateTime.of(2026 , 06 ,8,12,00,00))
                .build();
        Appointment appointment3 = Appointment.builder()
                .reason("body pain")
                .appointmentTime(LocalDateTime.of(2026 , 06 ,9,01,33,00))
                .build();
        Appointment apt1 = appointmentService.createAppointment(appointment1, 2L, 3L);
        Appointment apt2 = appointmentService.createAppointment(appointment2, 1L, 3L);
        Appointment apt3 = appointmentService.createAppointment(appointment3, 3L, 3L);
        System.out.println(apt1);
        System.out.println(apt2);
        System.out.println(apt3);
//        var appointment2 = appointmentService.reAssignmentAppointment(3L, appointment1.getId());
//        System.out.println(appointment2);
    }


    @Test
    public  void deletePatientTest(){
            patientService.deletePatientById(3L);
    }


    @Test
    public void fetchPatientsTest(){
        List<Patient> all = patientRepository.findAllPatientWithAppointment();
        System.out.println(all);
    }

}
