package com.example.authentication.service;

import com.example.authentication.model.payload.RegistrationRequest;
import com.example.authentication.persistence.entities.Role;
import com.example.authentication.persistence.entities.User;
import com.example.authentication.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleService roleService;
    public UserService(UserRepository userRepository,RoleService roleService){
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User createUser(RegistrationRequest registerRequest){
        User newUser = new User();
        Boolean isNewUserAsAdmin = registerRequest.getRegisterAsAdmin();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setUsername(registerRequest.getUsername());
        newUser.addRoles(getRolesForNewUser(isNewUserAsAdmin));
        newUser.setActive(true);
        newUser.setIsEmailVerified(true);
        newUser.setUserId(UUID.randomUUID().toString());
        return newUser;
    }

    private Set<Role> getRolesForNewUser(Boolean isToBeMadeAdmin){
        Set<Role>  newUserRoles = (Set<Role>) new HashSet<>(roleService.findAll());
        if(!isToBeMadeAdmin){
            newUserRoles.removeIf(Role::isAdminRole);
        }
        LOGGER.info("Setting user roles: " + newUserRoles);
        return newUserRoles;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
