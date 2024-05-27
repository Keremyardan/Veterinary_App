package com.dev_patika.veterinaryapp.dto.request.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest { // this data transfer object includes the information of the appointments that the animals have.

    @Min(value = 1, message = "ID can not be smaller than 1")
    private Long id;

    @NotNull(message = "Appointment date cannot be null.")
    private LocalDateTime date_time;

    @NotNull(message = "Animal ID cannot be null.")
    @Min(value = 1, message = "ID can not be smaller than 1")
    private Long animalId;

    @NotNull(message = "Doctor ID cannot be null.")
    @Min(value = 1, message = "Id 1'den küçük olamaz.")
    private Long doctorId;
}
