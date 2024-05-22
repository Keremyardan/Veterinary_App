package com.dev_patika.veterinaryapp.dto.request.vaccine;

import jakarta.persistence.NamedStoredProcedureQueries;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VaccineUpdateRequest {
    @Min(value = 1, message = "Vaccine ID can not be smaller than 1")
    private Long id;

    @NotNull(message = "Vaccine name can not be empty")
    private String name;

    @NotNull(message = "Vaccine code can not be empty")
    private String code;

    @NotNull(message = "Protection start date can not be empty")
    private LocalDate protectionStartDate;

    @NotNull(message = "Protection end date can not be empty")
    private LocalDate protectionEndDate;
}
