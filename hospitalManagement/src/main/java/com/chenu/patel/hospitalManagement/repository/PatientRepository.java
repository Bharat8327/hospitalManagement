package com.chenu.patel.hospitalManagement.repository;

import com.chenu.patel.hospitalManagement.dto.AppointmentResponseDto;
import com.chenu.patel.hospitalManagement.entity.Patient;
import com.chenu.patel.hospitalManagement.entity.type.BloodGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Spring Data JPA automatically creates query
    // select * from patient where name = ?
    Patient findByName(String name);

    // Find patient by birthDate and email
    List<Patient> findByBirthDateAndEmail(LocalDate birthDate, String email);

    // Find patients whose birthDate is between startDate and endDate
    List<Patient> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    // SQL like '%name%' and order by id desc
    List<Patient> findByNameContainingOrderByIdDesc(String name);

    // JPQL Query
    // Patient is Entity Name not table name
    // bloodGroup is entity field name not database column name
    @Query("SELECT p FROM Patient p WHERE p.bloodGroup = :bloodGroup")
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroup bloodGroup);

    // Get all patients born after given date
    @Query("SELECT p FROM Patient p WHERE p.birthDate > :birthDate")
    List<Patient> findAfterBornDate(@Param("birthDate") LocalDate birthDate);

    /*
      Projection Query

      Instead of fetching complete Patient entity,
      fetch only required data and map directly to DTO.

      Hibernate will call constructor:

      new ResponseBloodGroupCountEntity(
            p.bloodGroup,
            count(p)
      )

      This works only when DTO has matching constructor.
    */
    @Query("SELECT new com.chenu.patel.hospitalManagement.dto.ResponseBloodGroupCountEntity(p.bloodGroup,COUNT(p)) FROM Patient p GROUP BY p.bloodGroup")
    List<AppointmentResponseDto> findAllPatientByBloodGroupWithCount();

    /*
      Native Query

      Uses actual database SQL.

      Here patient is table name.

      Native query returns entity because
      selected columns match Patient entity mapping.
    */
    @Query(value = "SELECT * FROM patient", nativeQuery = true)
    Page<Patient> findAllPatient(Pageable pageable);

    /*
      Update Query

      @Modifying -> tells Spring this query modifies data

      @Transactional -> required because update/delete
      operations need transaction support

      Returns number of rows affected
    */
    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name = :name WHERE p.id = :id")
    int updatedRow(@Param("id") Long id,
                   @Param("name") String name);

//    @Query("Select p from Patient p left join fetch p.appointments a left join fetch a.doctor")
    @Query("Select p from Patient p left join fetch p.appointments ")
    List<Patient> findAllPatientWithAppointment();

}