package com.chenu.patel.hospitalManagement.repository;

import com.chenu.patel.hospitalManagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}