package com.example.bookstoreproject.service;

import com.example.bookstoreproject.dto.LoginDTO;
import com.example.bookstoreproject.dto.UserRegistrationDTO;
import com.example.bookstoreproject.entity.UserRegistrationData;
import com.example.bookstoreproject.exception.BookStoreException;
import com.example.bookstoreproject.repository.UserRegistrationRepository;
import com.example.bookstoreproject.util.EmailSenderService;
import com.example.bookstoreproject.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRegistrationService implements IUserRegistrationService{
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private EmailSenderService mailService;
    @Autowired
    private TokenUtil util;

    //save the userregistration details in repository
    @Override
    public String createUser(UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData= new UserRegistrationData(userRegistrationDTO);
        userRegistrationRepository.save(userRegistrationData);
        String token = util.createToken(userRegistrationData.getUserId());
        mailService.sendEmail(userRegistrationData.getEmail(), "Test Email", "Registered SuccessFully, hii: "
                +userRegistrationData.getFirstName()+"Please Click here to get data-> "
                +"http://localhost:8080/user/insert/"+token);
        return token;
    }

    //pass path variable as token and get all userregistration details from repository.
    @Override
    public List<UserRegistrationData> getAllUsers(String token) {
        int id=util.decodeToken(token);
        Optional<UserRegistrationData> isContactPresent=userRegistrationRepository.findById(id);
        if(isContactPresent.isPresent()) {
            List<UserRegistrationData> listAddressBook=userRegistrationRepository.findAll();
            mailService.sendEmail("akshuh818@gmail.com", "Test Email", "Get your data with this token, hii: "
                    +isContactPresent.get().getFirstName()+"Please Click here to get data-> "
                    +"http://localhost:8081/user/get/"+token);
            return listAddressBook;
        }else {
            System.out.println("Exception ...Token not found!");
            return null;
        }
    }

    //get userregistration details from repository by id .
    @Override
    public UserRegistrationData getUserById(String token) {
        int id=util.decodeToken(token);
        Optional<UserRegistrationData> getUser=userRegistrationRepository.findById(id);
        if(getUser.isPresent()){
            mailService.sendEmail("akshuh818@gmail.com", "Test Email", "Get your data with this token, hii: "
                    +getUser.get().getEmail()+"Please Click here to get data-> "
                    +"http://localhost:8080/user/get/"+token);
            return getUser.get();
        }
        else {
            throw new BookStoreException("Record for provided userId is not found");
        }
    }

    //get userregistration details from repository by email
    @Override
    public List<UserRegistrationData> getUserByEmail(String token) {
        List<UserRegistrationData> findEmail=userRegistrationRepository.findByEmail(token);
        if(findEmail.isEmpty()){
            throw new BookStoreException(" Details for provided user is not found");
        }
        return findEmail;
    }

    //update userregistration details by id
    @Override
    public UserRegistrationData updateUser(String token, UserRegistrationDTO userRegistrationDTO) {
        Integer id=util.decodeToken(token);
        Optional<UserRegistrationData> userRegistrationData = userRegistrationRepository.findById(id);
        if(userRegistrationData.isPresent()) {
            UserRegistrationData newBook = new UserRegistrationData(id,userRegistrationDTO);
            userRegistrationRepository.save(newBook);
            mailService.sendEmail(newBook.getEmail(), "Test Email", "Updated SuccessFully, hii: "
                    +newBook.getFirstName()+"Please Click here to get data of updated id-> "
                    +"http://localhost:8081/user/modify/"+token);
            return newBook;
        }
        throw new BookStoreException("User Details for id not found");
    }

    //user has to login through email and password
    @Override
    public Optional<UserRegistrationData> login(LoginDTO loginDTO) {
        Optional<UserRegistrationData> userRegistrationData=userRegistrationRepository.findByEmailAndPassword(loginDTO.getEmail(),loginDTO.getPassword());
        if(userRegistrationData.isPresent()){
            return userRegistrationData;
        }else {
            return null;
        }
    }
}
