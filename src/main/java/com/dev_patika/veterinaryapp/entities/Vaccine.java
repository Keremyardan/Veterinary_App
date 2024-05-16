package com.dev_patika.veterinaryapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "vaccines")
@NoArgsConstructor
@AllArgsConstructor

public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "vaccine_id")
    private Long id;


    @Column (name = "vaccine_name", nullable = false)
    private String name;


    @Column(name = "vaccine_code", nullable = false, unique = true)
    private String code;


    @Column(name = "vaccine_protectionStartDate", nullable = false)
    private LocalDate protectionStartDate;


    @Column (name = "vaccine_protectionFinishDate", nullable = false)
    private LocalDate protectionFinishDate;

    @ManyToMany(mappedBy = "vaccineList")
    List<Animal> animalList;


    @PrePersist
    public void generateCode() {
        this.code = UUID.randomUUID().toString();
    }


}
