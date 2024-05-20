package com.dev_patika.veterinaryapp.dto.request.animal;

import com.dev_patika.veterinaryapp.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {

    @NotNull (message = "Name can not be empty")
    @Size(min = 2, max = 50, message = "The name character amount should be between 2 and 50")
    private String name;

    @NotNull (message = "Specie can not be empty")
    @Size (min = 2, max = 50, message = "Specie character amount should be between 2 and 50")
    private String species;

    @NotNull(message = "Breed can not be empty")
    @Size (min = 2, max = 50, message = "Breed character amount should be between 2 and 50")
    private String breed;

    @NotNull(message = "Gender can not be empty")
    private Animal.GENDER gender;

    @NotNull(message = "Color can not be empty")
    @Size(min = 2, max = 50, message = "Color character amount should be between 2 and 50")
    private String color;

    @NotNull(message = "Date of birth can not be empty")
    private LocalDate dateOfBirth;

    @NotNull(message = "Customer ID can not be empty")
    @Min(value = 1, message = "Customer ID value should be greater than 1")
    private Long customerId;
}
