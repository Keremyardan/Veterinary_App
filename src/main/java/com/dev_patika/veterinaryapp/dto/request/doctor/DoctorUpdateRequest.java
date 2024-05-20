package com.dev_patika.veterinaryapp.dto.request.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {
    @Min(value = 1, message = "ID can not be smaller than 1")
    private Long id;

    @NotNull(message = "Doctor name can not be empty")
    private String name;

    @NotNull(message = "Phone number can not be empty")
    private String phone;

    @Email(message = "Please provide a valid email address")
    @NotNull(message = "Mail address can not be empty")
    private String email;

    @NotNull(message = "Address can not be empty")
    private String address;

    @NotNull(message = "City can not be empty")
    private String city;
}
