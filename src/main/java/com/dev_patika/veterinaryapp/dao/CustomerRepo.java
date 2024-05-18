package com.dev_patika.veterinaryapp.dao;

import com.dev_patika.veterinaryapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer WHERE LOWER(c.name) LIKE LOWER (CONCAT('%', :name, '%'))")
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);

    boolean existByEmail(String email);

    boolean existByPhone(String phone);
}
