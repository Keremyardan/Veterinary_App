package com.dev_patika.veterinaryapp.dao;

import com.dev_patika.veterinaryapp.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Long> {
    List<Vaccine> findByNameAndCode(String name, String code);

    List<Vaccine> findByProtectionEndDateBetween(LocalDate startDate, LocalDate endDate);

    List<Vaccine> findByAnimals_Id(Long animalId);

    boolean existsByName(String name);

    boolean existsByCode(String code);

}
