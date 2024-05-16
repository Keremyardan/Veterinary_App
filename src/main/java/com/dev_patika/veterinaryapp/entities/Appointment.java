package com.dev_patika.veterinaryapp.entities;



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


    @Column ( name = "appointmentDate", nullable = false)
    private LocalDate appointmentDate;

    @ManyToOne
    @JoinColumn(name="doctor_appointment_id",referencedColumnName = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name="appointment_availability_id",referencedColumnName = "available_date_id", nullable = false)
    private AvailableDate dateAvailable;
}
