package com.dev_patika.veterinaryapp.api;

import com.dev_patika.veterinaryapp.business.abstracts.IAnimalService;
import com.dev_patika.veterinaryapp.business.abstracts.ICustomerService;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.modelMapper.IModelMapperService;
import com.dev_patika.veterinaryapp.dto.request.animal.AnimalSaveRequest;
import com.dev_patika.veterinaryapp.dto.response.animal.AnimalResponse;
import com.dev_patika.veterinaryapp.entities.Animal;
import com.dev_patika.veterinaryapp.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// Endpoint for animal entity
@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    private final ICustomerService customerService;


// constructor with parameters
    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper, ICustomerService customerService) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
        this.customerService = customerService;

    }

    // endpoint for animal saving function
    @PostMapping("/animal")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        // This method saves the animal.
        Animal saveAnimal = this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);

        Customer customer = this.customerService.get(animalSaveRequest.getCustomerId());
        saveAnimal.setCustomer(customer);

        this.animalService.saveAnimal(saveAnimal);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAnimal, AnimalResponse.class));
    }

    // endpoint for animal update function
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@PathVariable("id") Long id, @Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        Animal animal = this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);

        Animal updatedAnimal = this.animalService.update(id,animal);

        return ResultHelper.success(this.modelMapper.forRequest().map(updatedAnimal,AnimalResponse.class));
    }

    // endpoint for animal finding by id function
    @GetMapping("/animal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> findAnimalById(@PathVariable("id") Long id) {
        Animal animal = this.animalService.getById(id);
        return ResultHelper.success(this.modelMapper.forRequest().map(animal, AnimalResponse.class));
    }
    // endpoint for animal delete function
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimalById(@PathVariable("id") Long id) {
        this.animalService.delete(id);
    }

    // endpoint for animal filter by name function
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResultData<List<AnimalResponse>> findAnimalByNameContainingIgnoreCase(@RequestParam("name") String name) {
        List<Animal> animals = this.animalService.findByNameContainingIgnoreCase(name);
        if (animals.isEmpty()) {
            return ResultHelper.animalNotFoundError();
        }
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(animalResponses);
    }
}
