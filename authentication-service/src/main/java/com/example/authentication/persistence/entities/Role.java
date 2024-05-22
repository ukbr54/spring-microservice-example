package com.example.authentication.persistence.entities;

import com.example.authentication.model.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @Column(name = "ROLE_NAME")
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> userList = new HashSet<>();

    public boolean isAdminRole(){
        return this.role.equals(RoleName.ROLE_ADMIN);
    }

    public Role(RoleName role) {
        this.role = role;
    }
}
