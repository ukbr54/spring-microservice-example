package com.example.authentication.persistence.entities;

import com.example.authentication.model.audit.DateAudit;
import com.example.authentication.validation.annotation.NullOrNotBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "users")
public class User extends DateAudit  {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "unique_id",unique = true,nullable = false,updatable = false)
    @NotNull(message = "UniqueId cannot be null")
    private String uniqueId;

    @Column(nullable = false,length = 120,unique = true)
    @NullOrNotBlank(message = "Email cannot be blank")
    @Email
    private String email;

    @Column(nullable = false,unique = true)
    @NotNull(message = "Password cannot be null")
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USER_AUTHORITY", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")})
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role){
        roles.add(role);
        role.getUserList().add(this);
    }

    public void addRoles(Set<Role> role){
        roles.forEach(this::addRole);
    }

    public void removeRole(Role role){
        roles.remove(role);
        role.getUserList().remove(this);
    }
    public User(){
        super();
    }
    public User(User user){
        userId = user.getUserId();
        password = user.getPassword();
        email = user.getEmail();
        active = user.getActive();
        roles = user.getRoles();
        isEmailVerified = user.getIsEmailVerified();
    }
}
