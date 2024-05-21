package com.dev_patika.veterinaryapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "animals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long id;


    @Column(name = "animal_name",nullable = false)
    private  String name ;


    @Column(name = "animal_species",nullable = false)
    private  String species ;


    @Column(name = "animal_breed",nullable = false)
    private  String breed ;


    @Column(name = "animal_gender",nullable = false)
    private String gender ;


    @Column(name = "animal_color",nullable = false)
    private  String color ;

    //@JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "animal_dateOfBirth",nullable = false)
    private LocalDate dateOfBirth;


    public enum GENDER {
        MALE,
        FEMALE
    }

    @ManyToMany()
    @JoinTable(
            name = "animal_vaccine",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "vaccine_id"))
    List<Vaccine> vaccineList;

    @ManyToOne
    @JoinColumn(name="animal_customer_id",referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "animal")
    private List<Appointment> appointmentList;
}
