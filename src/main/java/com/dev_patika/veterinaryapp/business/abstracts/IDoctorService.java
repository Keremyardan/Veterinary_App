package com.dev_patika.veterinaryapp.business.abstracts;

import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.entities.Doctor;
import org.springframework.data.domain.Page;

import javax.print.Doc;

public interface IDoctorService {
    ResultData<Doctor> save (Doctor doctor);

    Doctor get(Long id);

    void delete(Long id);

    ResultData<Doctor> update(Doctor doctor);

    Page<Doctor> cursor (int page, int size);
}
