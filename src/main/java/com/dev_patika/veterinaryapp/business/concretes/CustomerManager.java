package com.dev_patika.veterinaryapp.business.concretes;

import com.dev_patika.veterinaryapp.dao.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
public class CustomerManager {

    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;

    public CustomerManager(CustomerRepo customerRepo, ModelMapper modelMapper) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
    }






}
