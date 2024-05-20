package com.dev_patika.veterinaryapp.business.abstracts;

import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    ResultData<Customer> save(Customer customer); /////////////// buraya bakılacak

    Customer get(Long id);

    boolean delete(Long id);

    ResultData<Customer> update(Long id, Customer customer);

    Page<Customer> cursor(int page, int size);

    List<Customer> getCustomersByName(String name);


}
