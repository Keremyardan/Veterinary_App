package com.dev_patika.veterinaryapp.dao;

import com.dev_patika.veterinaryapp.entities.Animal;
import com.dev_patika.veterinaryapp.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    // this method finds the appointments by doctor id and appointments appointment date
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime dateTime);

    // This method finds the appointments by doctor id and appointment date between start date time and end date time.
    List<Appointment> findByDoctorIdAndAppointmentDateBetween (Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    // This method finds the appointments by appointment date between start date time and end date time and animal.
    List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Animal animal);

    List<Appointment> findByAnimalIdAndAppointmentDate (Long animalId, LocalDateTime dateTime);

    List<Appointment> findByAnimalId(Long animalId);
}
