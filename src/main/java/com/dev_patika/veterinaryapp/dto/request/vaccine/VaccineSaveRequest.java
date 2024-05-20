package com.dev_patika.veterinaryapp.dto.request.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {
    @NotNull(message = "Vaccine name can not be empty")
    private String name;

    @NotNull(message = "Vaccine code can not be empty")
    private String code;

    @NotNull(message = "Protection start date can not be empty")
    private LocalDate protectionStartDate;

    @NotNull(message = "Protection end date can not be empty")
    private LocalDate protectionEndDate;
}
