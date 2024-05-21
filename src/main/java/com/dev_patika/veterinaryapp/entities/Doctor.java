package com.dev_patika.veterinaryapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table (name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @Column(name = "doctor_id")
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "doctor_name", nullable = false)
    private String name;


    @Column (name = "doctor_phone", nullable = false)
    private String phone;


    @Column (name = "doctor_mail", nullable = false)
    private String email;


    @Column (name = "doctor_address", nullable = false)
    private String address;


    @Column ( name = "doctor_city", nullable = false)
    private String city;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointmentList;

    @ManyToMany()
    @JoinTable(
            name = "doctors_available_dates",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
    List<AvailableDate> availableDateList;
}
