package com.chenu.patel.hospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 50)
    private String specialization;
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "doctors",fetch = FetchType.LAZY)
    private Set<Department>departments = new HashSet<>();

    @OneToMany(mappedBy="doctor",fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();
}
