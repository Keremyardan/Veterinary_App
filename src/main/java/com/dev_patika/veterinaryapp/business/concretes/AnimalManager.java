package com.dev_patika.veterinaryapp.business.concretes;

import com.dev_patika.veterinaryapp.business.abstracts.IAnimalService;
import com.dev_patika.veterinaryapp.core.config.utilities.Msg;
import com.dev_patika.veterinaryapp.core.exceptions.NotFoundException;
import com.dev_patika.veterinaryapp.dao.AnimalRepo;
import com.dev_patika.veterinaryapp.dto.request.animal.AnimalSaveRequest;
import com.dev_patika.veterinaryapp.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// service layer for animal entity
@Service
public class AnimalManager implements IAnimalService {

    private final AnimalRepo animalRepo;
    public AnimalManager(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }

    @Override
    public Page<Animal> cursor(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.animalRepo.findAll(pageable);
    }

    // overriding for Ianimal service interface due to save an animal
    @Override
    public Animal saveAnimal(Animal animal) {
        return this.animalRepo.save(animal);
    }

    // overriding for Ianimal service interface due to update an animal
    @Override
    public Animal update(Long id, Animal animal) {
        Animal existingAnimal =  this.animalRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));

        existingAnimal.setName(animal.getName());
        existingAnimal.setSpecies(animal.getSpecies());
        existingAnimal.setBreed(animal.getBreed());
        existingAnimal.setGender(animal.getGender());
        existingAnimal.setColor(animal.getColor());
        existingAnimal.setDateOfBirth(animal.getDateOfBirth());
        existingAnimal.setCustomer(animal.getCustomer());

        return this.animalRepo.save(existingAnimal);
    }

    // overriding for Ianimal service interface due to get an animal by id
    @Override
    public Animal getById(Long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    // overriding for Ianimal service interface due to delete an animal
    @Override
    public void delete(Long id) {
        Animal animal = this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        this.animalRepo.delete(animal);
    }

    // overriding for Ianimal service interface due to find an animal by name
    @Override
    public List<Animal> findByNameContainingIgnoreCase(String name) {
        return this.animalRepo.findByNameContainingIgnoreCase(name);
    }

    // overriding for Ianimal service interface due to find an animal by customer id
    @Override
    public List<Animal> findByCustomerId(Long customerId) {
        return this.animalRepo.findAllByCustomerId(customerId);
    }
}
