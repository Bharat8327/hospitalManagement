package com.chenu.patel.hospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime appointmentTime;
    @Column(length = 500)
    private String reason;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false) // patient is required not nullable
    private Patient patient; // owning side


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false) // owning side
    private Doctor doctor;
}
