package com.dev_patika.veterinaryapp.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table (name = "available_dates")
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column (name = "available_date_id", columnDefinition = "serial")
    private Long id;


    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;


    @ManyToOne(fetch = FetchType.EAGER) // Many available dates can belong to one doctor.
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    /*
    @OneToMany(mappedBy = "availableDate")
    List<Appointment> appointmentList;

     */
}
