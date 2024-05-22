package com.example.authentication.model.payload;

import com.example.authentication.validation.annotation.NullOrNotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegistrationRequest {

    @NullOrNotBlank(message = "Registration email can be null but not blank")
    private String email;
    @NotNull(message = "Registration password cannot be null")
    private String password;
    @NullOrNotBlank(message = "Registration username can be null but not blank")
    private String username;
    @NotNull(message = "Specify whether the user has to be registered as an admin or not")
    private Boolean registerAsAdmin;

}
