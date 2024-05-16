package com.dev_patika.veterinaryapp.entities;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "appointment_id")
    private long id;

    @NotNull
    @Column ( name = "appointmentDate")
    private LocalDate appointmentDate;

}
