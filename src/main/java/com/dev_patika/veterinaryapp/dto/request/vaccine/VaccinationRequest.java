package com.dev_patika.veterinaryapp.dto.request.vaccine;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class VaccinationRequest {
    @NotNull(message = "Animal ID can not be empty")
    private Long animalID;

    @NotNull(message = "Vaccine ID can not be empty")
    private Long vaccineID;
}
