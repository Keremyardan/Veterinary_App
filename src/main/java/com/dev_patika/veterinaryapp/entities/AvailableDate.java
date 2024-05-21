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
    @Column (name = "available_date_id")
    private Long id;


    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;


    @ManyToMany(mappedBy = "availableDateList")
    List<Doctor> doctorList;

    @OneToMany(mappedBy = "dateAvailable")
    List<Appointment> appointmentList;
}
