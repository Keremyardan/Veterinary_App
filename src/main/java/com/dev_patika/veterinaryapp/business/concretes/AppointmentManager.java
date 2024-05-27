package com.dev_patika.veterinaryapp.business.concretes;

import com.dev_patika.veterinaryapp.business.abstracts.IAppointmentService;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.Msg;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.exceptions.NotFoundException;
import com.dev_patika.veterinaryapp.dao.AnimalRepo;
import com.dev_patika.veterinaryapp.dao.AppointmentRepo;
import com.dev_patika.veterinaryapp.dao.AvailableDateRepo;
import com.dev_patika.veterinaryapp.entities.Animal;
import com.dev_patika.veterinaryapp.entities.Appointment;
import com.dev_patika.veterinaryapp.entities.AvailableDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// service layer for appointment entity
@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AvailableDateRepo availableDateRepo;
    private final AnimalRepo animalRepo;

// constructor with parameters
    public AppointmentManager(AppointmentRepo appointmentRepo, AvailableDateRepo availableDateRepo, AnimalRepo animalRepo) {
        this.appointmentRepo = appointmentRepo;
        this.availableDateRepo = availableDateRepo;
        this.animalRepo = animalRepo;
    }

// overriding Iappointment manager function to create an appointment
    @Override
    public ResultData<Appointment> create(Appointment appointment, LocalDateTime dateTime) {
        // checks if the animal exists
        Animal animal = animalRepo.findById(appointment.getAnimal().getId())
                .orElseThrow(() -> new NotFoundException("Animal with id " + appointment.getAnimal().getId() + " not found"));
        if (animal == null) {
            return ResultHelper.animalNotFoundError();
        }


        // checks if the doctor is available at the given date
        if (!availableDateRepo.isDoctorAvailable(appointment.getDoctor().getId(), dateTime.toLocalDate())) {
            return ResultHelper.doctorNotAvailable();
        }

        // checks if the doctor already has an appointment at the given date and time
        List<Appointment> existingAppointments = appointmentRepo.findByDoctorIdAndAppointmentDate(appointment.getDoctor().getId(), dateTime);
        if (!existingAppointments.isEmpty()) {
            return ResultHelper.appointmentAlreadyExists();
        }

        // checks if the animal already has an appointment at any time
        List<Appointment> existingAnimalAppointments = appointmentRepo.findByAnimalId(appointment.getAnimal().getId());
        if (!existingAnimalAppointments.isEmpty()) {
            return ResultHelper.appointmentAlreadyExists();
        }

        // sets the appointment date
        appointment.setAppointmentDate(dateTime);

        // saves the new appointment and return it
        Appointment savedAppointment = appointmentRepo.save(appointment);
        return ResultHelper.created(savedAppointment);
    }

    // this method returns the appointments of the doctor between the given dates.
    @Override
    public List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        return this.appointmentRepo.findByDoctorIdAndAppointmentDateBetween(doctorId, startDateTime, endDateTime);
    }

    // this method returns the appointments of the animal between the given dates.
    @Override
    public List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Animal animal) {
        return this.appointmentRepo.findByAppointmentDateBetweenAndAnimal(startDateTime, endDateTime, animal);
    }

    // this method gets the appointment by id.
    @Override
    public Appointment get(Long id) {

        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    // overrides interface function to delete an appointment
    @Override
    public void delete(Long id) {
    Appointment appointment = this.get(id);
    this.appointmentRepo.delete(appointment);
    }

    @Override
    public ResultData<Appointment> update(Long id, Appointment appointment) {
        // checks if the appointment with the given id exists
        Appointment existingAppointment = this.appointmentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // checks if the animal exists
        Animal animal = animalRepo.findById(appointment.getAnimal().getId())
                .orElse(null);
        if (animal == null) {
            return ResultHelper.animalNotFoundError();
        }

        // updates the details of the existing appointment
        existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
        existingAppointment.setAnimal(appointment.getAnimal());
        existingAppointment.setDoctor(appointment.getDoctor());

        // saves the updated appointment in the database
        Appointment updatedAppointment = this.appointmentRepo.save(existingAppointment);

        // returns the updated appointment
        return ResultHelper.success(updatedAppointment);
    }

    @Override
    public Page<Appointment> cursor(int page, int size) {
        // this method returns the appointments with pagination.
        Pageable pageable = PageRequest.of(page, size);
        return this.appointmentRepo.findAll(pageable);
    }
}
