package com.chenu.patel.hospitalManagement.repository;

import com.chenu.patel.hospitalManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}