package com.example.authentication.persistence.entities;

import com.example.authentication.model.token.TokenStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Builder
public class EmailVerificationToken {

    @Id
    @Column(name = "token_id")
    @GeneratedValue
    private Long id;

    @Column(name = "token",nullable = false,unique = true)
    private String token;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private User user;

    @Column(name = "token_status")
    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    @Column(name = "expiry_date",nullable = false)
    private Instant expiryDate;

    public void setConfirmedStatus() {
        setTokenStatus(TokenStatus.STATUS_CONFIRMED);
    }
}
