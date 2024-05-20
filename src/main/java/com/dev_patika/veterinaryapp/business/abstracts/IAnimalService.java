package com.dev_patika.veterinaryapp.business.abstracts;

import com.dev_patika.veterinaryapp.dto.request.animal.AnimalSaveRequest;
import com.dev_patika.veterinaryapp.entities.Animal;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IAnimalService {

    Page<Animal> cursor(int page, int size);

    Animal saveAnimal(Animal animalSaveRequest);



}
