package com.dev_patika.veterinaryapp.entities;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "vaccines")
@NoArgsConstructor
@AllArgsConstructor

public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "vaccine")
    private Long id;

    @NotNull
    @Column (name = "vaccine_name")
    private String name;

    @NotNull
    @Column(name = "vaccine_code")
    private String code;

    @NotNull
    @Column(name = "vaccine_protectionStartDate")
    private LocalDate protectionStartDate;

    @NotNull
    @Column (name = "vaccine_protectionFinishDate")
    private LocalDate protectionFinishDate;
}
