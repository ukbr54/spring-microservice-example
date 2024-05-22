package com.example.authentication.service;

import com.example.authentication.exception.ResourceAlreadyInUseException;
import com.example.authentication.model.payload.RegistrationRequest;
import com.example.authentication.persistence.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    private final UserService userService;

    public AuthService(UserService userService){
        this.userService = userService;
    }

    public Optional<User> registerUser(RegistrationRequest newRegistrationRequest){
        String newRegistrationRequestEmail = newRegistrationRequest.getEmail();
        if(this.emailAlreadyExists(newRegistrationRequestEmail)){
            LOGGER.error("Email already exists: " + newRegistrationRequestEmail);
            throw new ResourceAlreadyInUseException("Email","Address",newRegistrationRequestEmail);
        }
        LOGGER.info("Trying to register new user ["+newRegistrationRequestEmail+"]");
        User newUser = userService.createUser(newRegistrationRequest);
        User registeredNewUser = userService.save(newUser);
        return Optional.of(registeredNewUser);
    }

    public Boolean emailAlreadyExists(String email){
        return userService.existsByEmail(email);
    }
}
