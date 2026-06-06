package com.chenu.patel.hospitalManagement.entity;

import com.chenu.patel.hospitalManagement.entity.type.BloodGroup;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@Table(name = "patient", uniqueConstraints = {@UniqueConstraint(name = "uk_patient_name",columnNames = {"name"})},
        indexes = {@Index(name = "index_patient_birthDate",columnList = "birthDate" )}
)

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    private String gender;

    @Column(unique = true, nullable = false)
    private String email;

    @ToString.Exclude
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},orphanRemoval = true ) // persist -> are used to first time save , merge are used for update
    @JoinColumn(name="patient_insurance_id") // owning side
    private Insurance insurance;

    @OneToMany(mappedBy = "patient" , cascade = {CascadeType.REMOVE},orphanRemoval = true ) // inverse side
    private List<Appointment> appointments =  new ArrayList<>();
}