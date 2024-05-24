package com.dev_patika.veterinaryapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointmentList;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AvailableDate> availableDateList;
}
