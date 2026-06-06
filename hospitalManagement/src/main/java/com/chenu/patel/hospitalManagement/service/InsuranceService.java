package com.chenu.patel.hospitalManagement.service;


import com.chenu.patel.hospitalManagement.entity.Insurance;
import com.chenu.patel.hospitalManagement.entity.Patient;
import com.chenu.patel.hospitalManagement.repository.InsuranceRepository;
import com.chenu.patel.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.FetchType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsurance(Insurance insurance ,Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found by id " + patientId));
        patient.setInsurance(insurance);

        insurance.setPatient(patient);
        return patient;
    }

    @Transactional
    public Patient disaccociateInsuranceFromPatient(Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found by id " + patientId));
            patient.setInsurance(null);
            return patient;
    }
}
