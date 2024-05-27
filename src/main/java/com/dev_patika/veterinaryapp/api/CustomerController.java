package com.dev_patika.veterinaryapp.api;

import com.dev_patika.veterinaryapp.business.abstracts.IAnimalService;
import com.dev_patika.veterinaryapp.business.abstracts.ICustomerService;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.modelMapper.IModelMapperService;
import com.dev_patika.veterinaryapp.dto.request.animal.AnimalSaveRequest;
import com.dev_patika.veterinaryapp.dto.request.customer.CustomerSaveRequest;
import com.dev_patika.veterinaryapp.dto.request.customer.CustomerUpdateRequest;
import com.dev_patika.veterinaryapp.dto.response.CursorResponse;
import com.dev_patika.veterinaryapp.dto.response.animal.AnimalResponse;
import com.dev_patika.veterinaryapp.dto.response.customer.CustomerResponse;
import com.dev_patika.veterinaryapp.entities.Animal;
import com.dev_patika.veterinaryapp.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// endpoint creation for customers
@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;

    private final IAnimalService animalService;

    private final IModelMapperService modelMapperService;

    //constructor with parameters
    public CustomerController(ICustomerService customerService, IAnimalService animalService, IModelMapperService modelMapperService) {
        this.customerService = customerService;
        this.animalService = animalService;
        this.modelMapperService = modelMapperService;
    }

    //code block for customer save function
    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {
        // This method saves the customer.
        Customer saveCustomer = this.modelMapperService.forRequest().map(customerSaveRequest, Customer.class);
        ResultData<Customer> result = this.customerService.save(saveCustomer);

        // Check if a customer with the same email or phone already exists
        if (!result.isSuccess()) {
            return new ResultData<>(false, result.getMessage(), "400", null);
        }

        return ResultHelper.created(this.modelMapperService.forResponse().map(result.getData(), CustomerResponse.class));
    }

    //code block for customer update fundtion
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        // Map the request to a Customer object
        Customer customer = this.modelMapperService.forRequest().map(customerUpdateRequest, Customer.class);

        // Call the update method in the CustomerManager class
        ResultData<Customer> resultData = this.customerService.update(customerUpdateRequest.getId(), customer);

        // If the update was not successful, return the error message
        if (!resultData.isSuccess()) {
            return new ResultData<>(false, resultData.getMessage(), "400", null);
        }

        // Map the updated customer to a CustomerResponse object
        CustomerResponse customerResponse = this.modelMapperService.forResponse().map(resultData.getData(), CustomerResponse.class);

        // Return the updated customer
        return ResultHelper.success(customerResponse);
    }

    // code block for customer get function by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("id") Long id) {
        // This method gets the customer by id.
        Customer customer = this.customerService.get(id);
        return ResultHelper.success(this.modelMapperService.forResponse().map(customer, CustomerResponse.class));
    }

    //customer deletion function
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomerById(@PathVariable("id") Long id) {
        this.customerService.delete(id);
    }

    // this method returns the customers by name.
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> getCustomersByName(@RequestParam("name") String name) {

        List<Customer> customers = this.customerService.getCustomersByName(name);
        List<CustomerResponse> customerResponses = customers.stream()
                .map(customer -> this.modelMapperService.forResponse().map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(customerResponses);
    }

    // this method returns the animals by customer id.
    @GetMapping("/{id}/animal")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> getAnimals(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        Page<Animal> animals = this.animalService.cursor(page, size);
        List<Animal> animalList = this.animalService.findByCustomerId(id);
        List<AnimalResponse> animalResponseList = new ArrayList<>();
        for (Animal animal : animalList) {
            animalResponseList.add(this.modelMapperService.forResponse().map(animal, AnimalResponse.class));
        }
        Page<AnimalResponse> animalResponsePage = animals
                .map(animal -> this.modelMapperService.forResponse().map(animal, AnimalResponse.class));
        return ResultHelper.cursor(animalResponsePage);
    }

}
