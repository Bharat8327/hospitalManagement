package com.chenu.patel.hospitalManagement.repository;

import com.chenu.patel.hospitalManagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}