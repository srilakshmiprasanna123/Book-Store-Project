package com.example.bookstoreproject.service;

import com.example.bookstoreproject.dto.LoginDTO;
import com.example.bookstoreproject.dto.UserRegistrationDTO;
import com.example.bookstoreproject.entity.UserRegistrationData;

import java.util.List;
import java.util.Optional;

public interface IUserRegistrationService {
    String createUser(UserRegistrationDTO userRegistrationDTO);
    List<UserRegistrationData> getAllUsers(String token);
    UserRegistrationData getUserById(String token);
    List<UserRegistrationData> getUserByEmail(String token);
    UserRegistrationData updateUser(String token,UserRegistrationDTO userRegistrationDTO);
    Optional<UserRegistrationData> login(LoginDTO loginDTO);
}
