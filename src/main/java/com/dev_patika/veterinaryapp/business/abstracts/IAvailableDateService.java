package com.dev_patika.veterinaryapp.business.abstracts;

import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.entities.AvailableDate;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {
    ResultData<AvailableDate> save(AvailableDate availableDate);

    AvailableDate get(Long id);

    boolean delete(Long id);

    AvailableDate update(AvailableDate availableDate);

    Page<AvailableDate> cursor(int page, int size);

    List<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate date);

    boolean isDoctorAvailable(Long doctorId, LocalDate date);

    List<AvailableDate> findAvailableDatesByDoctorId (Long doctorId);
}
