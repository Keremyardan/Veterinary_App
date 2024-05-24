package com.dev_patika.veterinaryapp.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
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


    @Column (name = "vaccine_protectionEndDate", nullable = false)
    private LocalDate protectionEndDate;

    @OneToMany(mappedBy = "vaccine", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Animal> animals;


    /*
    @PrePersist
    public void generateCode() {
        this.code = UUID.randomUUID().toString();
    }

     */


}
