package com.dev_patika.veterinaryapp.dto.response.animal;

import com.dev_patika.veterinaryapp.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {
    private Long id;

    private String name;

    private String species;

    private String breed;

    private Animal.GENDER gender;

    private String color;

    private LocalDate dateOfBirth;

    private Long customerId;
}
