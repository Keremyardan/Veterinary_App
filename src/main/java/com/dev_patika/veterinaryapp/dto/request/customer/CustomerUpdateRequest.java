package com.dev_patika.veterinaryapp.dto.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest { // this data transfer object includes the information of the customers that the animals have.

    @Min(value = 1, message = "Id 1'den küçük olamaz.")
    private Long id;

    @NotNull(message = "Müşteri adı boş olamaz.")
    private String name;

    @NotNull(message = "Müşteri numarası boş olamaz.")
    private String phone;

    @Email(message = "Geçerli bir mail adresi giriniz.")
    private String email;

    private String address;

    private String city;
}
