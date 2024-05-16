package com.dev_patika.veterinaryapp.entities;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

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

    @NotNull
    @Column(name = "doctor_name")
    private String name;

    @NotNull
    @Column (name = "doctor_phone")
    private String phone;

    @NotNull
    @Column (name = "doctor_mail")
    private String mail;

    @NotNull
    @Column (name = "doctor_address")
    private String address;

    @NotNull
    @Column ( name = "doctor_city")
    private String city;

}
