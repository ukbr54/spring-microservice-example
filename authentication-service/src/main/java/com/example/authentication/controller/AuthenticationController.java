package com.example.authentication.controller;

import com.example.authentication.event.OnUserRegistrationCompleteEvent;
import com.example.authentication.exception.UserRegistrationException;
import com.example.authentication.model.payload.ApiResponse;
import com.example.authentication.model.payload.RegistrationRequest;
import com.example.authentication.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthService authService;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    public AuthenticationController(AuthService authService,ApplicationEventPublisher applicationEventPublishe){
        this.authService = authService;
        this.applicationEventPublisher = applicationEventPublishe;
    }

    @GetMapping("/info")
    public String info(){
        return "Spring security Authenticated successfully!!";
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationRequest registrationRequest){
        return authService.registerUser(registrationRequest)
                .map(user -> {
                    UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/registrationConfirmation");
                    OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent = new OnUserRegistrationCompleteEvent(user, uriBuilder);
                    applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
                    LOGGER.info("Registered user returned [API[:"+user);
                    return ResponseEntity.ok(new ApiResponse(true,"User registered successfully. Check your mail for verification"));
                }).orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(), "Missing user object in database"));
    }
}
