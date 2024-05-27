package com.dev_patika.veterinaryapp.business.concretes;

import com.dev_patika.veterinaryapp.business.abstracts.IVaccineService;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.Msg;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.exceptions.NotFoundException;
import com.dev_patika.veterinaryapp.dao.VaccineRepo;
import com.dev_patika.veterinaryapp.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// service layer for vaccine entity
@Service
public class VaccineManager implements IVaccineService {

    private final VaccineRepo vaccineRepo;

    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    // save method
    @Override
    public ResultData<Vaccine> save(Vaccine vaccine) {
        // checks if a vaccine with the same name or code already exists
        if (vaccineRepo.existsByCode(vaccine.getCode())) {
            return ResultHelper.vaccineNameAndCodeExists();
        }

        // saves the new vaccine and return it
        Vaccine savedVaccine = vaccineRepo.save(vaccine);
        return ResultHelper.created(savedVaccine);
    }

    @Override
    public Vaccine get(Long id) {
        // this method gets the vaccine by id.
        return this.vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    //vaccine delete method
    @Override
    public void delete(Long id) {
        Vaccine vaccine = this.vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        this.vaccineRepo.delete(vaccine);
    }

    //update method for vaccine
    @Override
    public ResultData<Vaccine> update(Long id,Vaccine vaccine) {
        // checks if the vaccine exists
        Vaccine existingVaccine = this.vaccineRepo.findById(vaccine.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // checks if a vaccine with the same name or code already exists
        if (vaccineRepo.existsByName(vaccine.getName())) {
            return ResultHelper.vaccineNameAndCodeExists();
        }
        if (vaccineRepo.existsByCode(vaccine.getCode())) {
            return ResultHelper.vaccineNameAndCodeExists();
        }

        // updates the vaccine
        existingVaccine.setName(vaccine.getName());
        existingVaccine.setCode(vaccine.getCode());
        existingVaccine.setProtectionStartDate(vaccine.getProtectionStartDate());
        existingVaccine.setProtectionEndDate(vaccine.getProtectionEndDate());

        return ResultHelper.success(this.vaccineRepo.save(existingVaccine));
    }

    @Override
    public Page<Vaccine> cursor(int page, int size) {
        // This method returns the vaccines by page.
        Pageable pageable = PageRequest.of(page, size);
        return this.vaccineRepo.findAll(pageable);
    }

    // find vaccines by animal id
    @Override
    public List<Vaccine> findByAnimalId(Long animalId) {
        return this.vaccineRepo.findByAnimals_Id(animalId);
    }

    @Override
    public List<Vaccine> findByProtectionEndDateBetween(LocalDate startDate, LocalDate endDate) {
        // this method finds the vaccines by protection end date between start date and end date.
        return this.vaccineRepo.findByProtectionEndDateBetween(startDate, endDate);
    }

    // find vaccines by name and code
    @Override
    public Vaccine findVaccineByNameAndCode(String name, String code) {
        return this.vaccineRepo.findByNameAndCode(code,name);
    }


}
