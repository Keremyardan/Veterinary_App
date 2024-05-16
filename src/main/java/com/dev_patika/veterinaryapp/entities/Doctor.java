package com.dev_patika.veterinaryapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table (name = "doctors")
@Data
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
    private String mail;


    @Column (name = "doctor_address", nullable = false)
    private String address;


    @Column ( name = "doctor_city", nullable = false)
    private String city;

}
