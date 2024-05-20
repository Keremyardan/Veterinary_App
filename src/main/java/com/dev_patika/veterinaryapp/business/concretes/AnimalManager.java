package com.dev_patika.veterinaryapp.business.concretes;

import com.dev_patika.veterinaryapp.business.abstracts.IAnimalService;
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




}
