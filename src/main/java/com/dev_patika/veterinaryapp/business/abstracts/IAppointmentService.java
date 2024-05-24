package com.dev_patika.veterinaryapp.business.abstracts;

import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.entities.Animal;
import com.dev_patika.veterinaryapp.entities.Appointment;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    ResultData<Appointment> create(Appointment appointment, LocalDateTime dateTime);

    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDateTime,LocalDateTime endDateTime);

    List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Animal animal);

    Appointment get (Long id);

    void delete(Long id);

    ResultData<Appointment> update(Long id, Appointment appointment);

    Page<Appointment> cursor (int page, int size);
}
