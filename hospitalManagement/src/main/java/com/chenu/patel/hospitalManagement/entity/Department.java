package com.chenu.patel.hospitalManagement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true,nullable=false,length=50)
    private String name;

    @OneToOne
    @JoinColumn(nullable=false)
    private Doctor headDoctor; // owning side

    @ManyToMany
    @JoinTable(
            name="my_dpt_doctors",
            joinColumns=@JoinColumn(name="dpt_id"),
            inverseJoinColumns=@JoinColumn(name="doctor_id")
    )
    private Set<Doctor> doctors = new HashSet<>();
}
