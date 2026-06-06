package com.chenu.patel.hospitalManagement.repository;

import com.chenu.patel.hospitalManagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}