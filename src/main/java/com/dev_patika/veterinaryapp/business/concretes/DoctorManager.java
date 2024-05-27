package com.dev_patika.veterinaryapp.business.concretes;

import com.dev_patika.veterinaryapp.business.abstracts.IDoctorService;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.Msg;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.exceptions.NotFoundException;
import com.dev_patika.veterinaryapp.dao.DoctorRepo;
import com.dev_patika.veterinaryapp.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.print.Doc;


//service layer for doctor entity
@Service
public class DoctorManager implements IDoctorService {

    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    // save method for doctor
    @Override
    public ResultData<Doctor> save(Doctor doctor) {
        if(doctorRepo.existsByEmail(doctor.getEmail())) {
            return ResultHelper.EmailExists();
        }
        if(doctorRepo.existsByPhone(doctor.getEmail())) {
            return ResultHelper.PhoneExists();
        }

        Doctor saveDoctor = doctorRepo.save(doctor);
        return ResultHelper.created(doctor);
    }

    //get doctor by id
    @Override
    public Doctor get(Long id) {
       return this.doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    // delete doctor by id
    @Override
    public void delete(Long id) {
        Doctor doctor = this.doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        this.doctorRepo.delete(doctor);
    }

    // doctor update method
    @Override
    public ResultData<Doctor> update(Doctor doctor) {
        // retrieves the existing doctor by ID
        Doctor existingDoctor = doctorRepo.findById(doctor.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // checks if a doctor with the same email already exists and is not the current doctor
        if (doctorRepo.existsByEmail(doctor.getEmail()) && !existingDoctor.getEmail().equals(doctor.getEmail())) {
            return ResultHelper.EmailExists();
        }

        // checks if a doctor with the same phone already exists and is not the current doctor
        if(doctorRepo.existsByPhone(doctor.getPhone())&& !existingDoctor.getPhone().equals(doctor.getPhone())) {
            return ResultHelper.PhoneExists();
        }

        Doctor existedDoctor = this.doctorRepo.findById(doctor.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        existedDoctor.setName(doctor.getName());
        existedDoctor.setPhone(doctor.getPhone());
        existedDoctor.setEmail(doctor.getEmail());
        existedDoctor.setAddress(doctor.getAddress());
        existedDoctor.setCity(doctor.getCity());
        return ResultHelper.success(this.doctorRepo.save(existedDoctor));

    }

    @Override
    public Page<Doctor> cursor(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return this.doctorRepo.findAll(pageable);
    }
}
