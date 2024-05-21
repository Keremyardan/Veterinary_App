package com.dev_patika.veterinaryapp.dao;

import com.dev_patika.veterinaryapp.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    boolean existsByPhone(String phone);
    boolean existsByEmail (String email);
}
