package com.example.authentication.service;

import com.example.authentication.persistence.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Collection<?> findAll(){
        return roleRepository.findAll();
    }
}
