package com.chenu.patel.hospitalManagement.service;

import com.chenu.patel.hospitalManagement.entity.Patient;
import com.chenu.patel.hospitalManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    public List<Patient> getPatientById(Long id){
//        Patient p1 = patientRepository.findById(id).orElseThrow(()-> new NoSuchElementException("fake1"));
//        Patient p2 = patientRepository.findById(id).orElseThrow(()-> new NoSuchElementException("fake2"));
//
//        System.out.println(p1==p2);
//        p1.setName("yoyo"); // direty checking and apply update query
//        return p1;
//        Patient patient = patientRepository.findByName("John Doe");
       List< Patient> p = patientRepository.findByBirthDateAndEmail(LocalDate.of(1995,01,10),"michael.johnson@example.com");
        List<Patient> p1 = patientRepository.findByBirthDateBetween(LocalDate.of(1988,01,01),LocalDate.of(1990,01,01));
        return p1;
    }


    @Transactional
    public void deletePatientById(Long id){
            Patient p = patientRepository.findById(id).orElseThrow();
            patientRepository.delete(p);
    }

}
