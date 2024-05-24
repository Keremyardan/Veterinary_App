package com.dev_patika.veterinaryapp.business.abstracts;

import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.dto.request.vaccine.VaccinationRequest;
import com.dev_patika.veterinaryapp.entities.Vaccine;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {
    ResultData<Vaccine> save(Vaccine vaccine);

    Vaccine get(Long id);

    void delete(Long id);

    ResultData<Vaccine> update(Long id, Vaccine vaccine);

    Page<Vaccine> cursor(int page, int size);

    List<Vaccine> findByAnimalId(Long animalId);

    List<Vaccine> findByProtectionEndDateBetween(LocalDate startDate, LocalDate endDate);

    Vaccine findVaccineByNameAndCode(String name, String code);
}
