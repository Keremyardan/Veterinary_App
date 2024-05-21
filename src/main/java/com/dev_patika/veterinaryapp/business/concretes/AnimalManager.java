package com.dev_patika.veterinaryapp.business.concretes;

import com.dev_patika.veterinaryapp.business.abstracts.IAnimalService;
import com.dev_patika.veterinaryapp.core.config.utilities.Msg;
import com.dev_patika.veterinaryapp.core.exceptions.NotFoundException;
import com.dev_patika.veterinaryapp.dao.AnimalRepo;
import com.dev_patika.veterinaryapp.dto.request.animal.AnimalSaveRequest;
import com.dev_patika.veterinaryapp.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalManager implements IAnimalService {

    private final AnimalRepo animalRepo;
    public AnimalManager(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }

    @Override
    public Page<Animal> cursor(int page, int size) {
        return null;
    }

    @Override
    public Animal saveAnimal(Animal animal) {
        return this.animalRepo.save(animal);
    }

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

    @Override
    public Animal getById(Long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        Animal animal = this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        this.animalRepo.delete(animal);
    }

}
