package com.example.bookstoreproject.repository;

import com.example.bookstoreproject.entity.UserRegistrationData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserRegistrationData,Integer> {
    List<UserRegistrationData> findByEmail(String email);

    Optional<UserRegistrationData> findByEmailAndPassword(String email, String password);
}