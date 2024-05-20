package com.dev_patika.veterinaryapp.dto.request.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {
    @NotNull(message = "Doctor name can not be empty")
    private String name;

    @NotNull(message = "Phone number can not be empty")
    private String number;

    @Email(message = "Provide a valid mail address")
    @NotNull(message = "Mail address can not be empty")
    private String email;

    @NotNull(message = "Address can not be empty")
    private String address;

    @NotNull(message = "City can not be empty")
    private String city;
}
