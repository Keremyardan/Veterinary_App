package com.dev_patika.veterinaryapp.dto.request.availabledate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateUpdateRequest { // This data transfer object includes the information of the available dates that the doctors have.

    @Min(value = 1, message = "ID can not be smaller than 1")
    private Long id;

    @NotNull(message = "Available date cannot be null.")
    private LocalDate availableDate;

    @NotNull(message = "Doctor ID cannot be null.")
    @Min(value = 1, message = "ID can not be smaller than 1")
    private Long doctorId;
}
