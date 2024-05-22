package com.dev_patika.veterinaryapp.business.abstracts;

import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.entities.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ICustomerService {

    ResultData<Customer> save(Customer customer);

    Customer get(Long id);

    void delete(Long id);

    ResultData<Customer> update(Long id, Customer customer);

    Page<Customer> cursor(int page, int size);

    List<Customer> getCustomersByName(String name);


}
