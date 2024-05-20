package com.dev_patika.veterinaryapp.business.abstracts;

import com.dev_patika.veterinaryapp.entities.Animal;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IAnimalService {
    Animal save(Animal animal);

    Animal get(Long id);

    boolean delete(Long id);

    Animal update(Long id, Animal animal);

    Page<Animal> cursor(int page, int size);

    List<Animal> findByCustomerId(Long customerId);

    List<Animal> findByVaccineId(Long vaccineId);

    List<Animal> findByNameContainingIgnoreCase(String name);

    List<Animal> findAllByVaccineProtectionStartDateGreaterThanEqualAndVaccineProtectionEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);
}
