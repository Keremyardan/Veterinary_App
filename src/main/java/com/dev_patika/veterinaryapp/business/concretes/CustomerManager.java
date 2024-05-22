package com.dev_patika.veterinaryapp.business.concretes;

import com.dev_patika.veterinaryapp.business.abstracts.ICustomerService;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.Msg;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.exceptions.NotFoundException;
import com.dev_patika.veterinaryapp.dao.CustomerRepo;
import com.dev_patika.veterinaryapp.entities.Animal;
import com.dev_patika.veterinaryapp.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class CustomerManager implements ICustomerService {

    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;

    public CustomerManager(CustomerRepo customerRepo, ModelMapper modelMapper) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResultData<Customer> save(Customer customer) {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            return ResultHelper.EmailExists();
        }
        if (customerRepo.existsByPhone(customer.getPhone())) {
            return ResultHelper.PhoneExists();
        }

        // Save the new customer and return it
        Customer savedCustomer = customerRepo.save(customer);
        return ResultHelper.created(savedCustomer);
    }

    @Override
    public Customer get(Long id) {
        // This method gets the customer by id.
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        Customer customer = this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        this.customerRepo.delete(customer);
    }

    @Override
    public ResultData<Customer> update(Long id, Customer customer) {
        // Check if the customer with the given id exists
        Customer existingCustomer = this.customerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // Update the details of the existing customer
        existingCustomer.setName(customer.getName());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setCity(customer.getCity());

        // Save the updated customer in the database
        Customer updatedCustomer = this.customerRepo.save(existingCustomer);

        // Return the updated customer
        return ResultHelper.success(updatedCustomer);
    }


    @Override
    public Page<Customer> cursor(int page, int size) {
        return null;
    }

    @Override
    public List<Customer> getCustomersByName(String name) {
        return customerRepo.findByNameContainingIgnoreCase(name);
    }
}
