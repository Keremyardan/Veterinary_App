package com.dev_patika.veterinaryapp.dto.request.availabledate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateSaveRequest { // This data transfer object includes the information of the available dates that the doctors have.
    @NotNull(message = "Available date cannot be null.")
    private LocalDate availableDate;

    @NotNull(message = "Doctor ID cannot be null.")
    private Long doctorId;
}
