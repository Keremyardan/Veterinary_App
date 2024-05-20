package com.dev_patika.veterinaryapp.dto.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {
    @NotNull(message = "Customer name can not be empty")
    private String name;

    @NotNull(message = "Customer phone number can not be empty")
    private String number;

    @NotNull(message = "Enter a valid mail address")
    private String email;

    private String address;

    private String city;
}
