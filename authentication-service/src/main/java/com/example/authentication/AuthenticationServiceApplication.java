package com.example.authentication;

import com.example.authentication.model.RoleName;
import com.example.authentication.persistence.entities.Role;
import com.example.authentication.persistence.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AuthenticationServiceApplication {
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner startup(){
		return args -> {
			Role userRole = new Role(RoleName.ROLE_USER);
			Role adminRole = new Role(RoleName.ROLE_ADMIN);
			roleRepository.saveAll(List.of(userRole,adminRole));
		};
	}
}
