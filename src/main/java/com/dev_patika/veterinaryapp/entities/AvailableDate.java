package com.dev_patika.veterinaryapp.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table (name = "availableDates")
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column (name = "availableDate_id")
    private Long id;


    @Column(name = "availableDate", nullable = false)
    private LocalDate availabledate;

}
