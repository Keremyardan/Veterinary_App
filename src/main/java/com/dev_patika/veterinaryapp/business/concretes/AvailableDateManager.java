package com.dev_patika.veterinaryapp.business.concretes;

import com.dev_patika.veterinaryapp.business.abstracts.IAvailableDateService;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.Msg;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.exceptions.NotFoundException;
import com.dev_patika.veterinaryapp.dao.AnimalRepo;
import com.dev_patika.veterinaryapp.dao.AvailableDateRepo;
import com.dev_patika.veterinaryapp.dao.DoctorRepo;
import com.dev_patika.veterinaryapp.entities.AvailableDate;
import com.dev_patika.veterinaryapp.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class AvailableDateManager implements IAvailableDateService {

    private final AvailableDateRepo availableDateRepo;

    private final AnimalRepo animalRepo;

    private final DoctorRepo doctorRepo;

    public AvailableDateManager(AvailableDateRepo availableDateRepo, AnimalRepo animalRepo, DoctorRepo doctorRepo) {
        this.availableDateRepo = availableDateRepo;
        this.animalRepo = animalRepo;
        this.doctorRepo = doctorRepo;
    }


    @Override
    public ResultData<AvailableDate> save(AvailableDate availableDate) {
        // Check if the doctor with the given id exists
        Doctor doctor = doctorRepo.findById(availableDate.getDoctor().getId())
                .orElseThrow(() -> new NotFoundException("Doctor with id " + availableDate.getDoctor().getId() + " not found"));

        // Check if an available date for the same doctor and date already exists
        List<AvailableDate> existingDates = availableDateRepo.findByDoctorIdAndAvailableDate(doctor.getId(), availableDate.getAvailableDate());
        if (!existingDates.isEmpty()) {
            return ResultHelper.doctorNotAvailable();
        }

        // Save the available date
        return ResultHelper.created(availableDateRepo.save(availableDate));
    }

    @Override
    public AvailableDate get(Long id) {
        // This method gets the available date by id.
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(Long id) {
        // This method deletes the available date by id.
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        // Check if the available date with the given id exists
        AvailableDate existingAvailableDate = this.availableDateRepo.findById(availableDate.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // Update the details of the existing available date
        existingAvailableDate.setAvailableDate(availableDate.getAvailableDate());
        existingAvailableDate.setDoctor(availableDate.getDoctor());

        // Save the updated available date in the database
        AvailableDate updatedAvailableDate = this.availableDateRepo.save(existingAvailableDate);

        // Return the updated available date
        return updatedAvailableDate;
    }

    @Override
    public Page<AvailableDate> cursor(int page, int size) {
        // This method returns the available dates with pagination.
        Pageable pageable = PageRequest.of(page, size);
        return this.availableDateRepo.findAll(pageable);
    }

    @Override
    public List<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate date) {
        // This method returns the available dates by doctor id and date.
        return this.availableDateRepo.findByDoctorIdAndAvailableDate(doctorId, date);
    }

    @Override
    public boolean isDoctorAvailable(Long doctorId, LocalDate date) {
        List<AvailableDate> availableDates = availableDateRepo.findByDoctorIdAndAvailableDate(doctorId, date);
        return !availableDates.isEmpty();
    }

    @Override
    public List<AvailableDate> findAvailableDatesByDoctorId(Long doctorId) {


        List<AvailableDate> availableDates = availableDateRepo.findAvailableDatesByDoctorId(doctorId);
        System.out.println(availableDates);
        return availableDates;


    }
}
