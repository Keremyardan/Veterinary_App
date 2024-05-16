package com.dev_patika.veterinaryapp.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table (name = "availableDates")
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column (name = "available_date_id")
    private Long id;


    @Column(name = "availableDate", nullable = false)
    private LocalDate availabledate;


    @ManyToMany(mappedBy = "availableDateList")
    List<Doctor> doctorList;

    @OneToMany(mappedBy = "dateAvailable")
    List<Appointment> appointmentList;
}
