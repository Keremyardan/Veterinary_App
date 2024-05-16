package com.dev_patika.veterinaryapp.entities;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @NotNull
    @Column(name = "customer_name")
    private  String name ;

    @NotNull
    @Column(name = "customer_phone")
    private  String phone ;

    @NotNull
    @Column(name = "customer_mail")
    private  String mail ;

    @NotNull
    @Column(name = "customer_address")
    private  String address ;

    @NotNull
    @Column(name = "customer_city")
    private  String city ;
}
