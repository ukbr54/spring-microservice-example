package com.example.authentication.service;

import com.example.authentication.exception.InvalidTokenRequestException;
import com.example.authentication.model.token.TokenStatus;
import com.example.authentication.persistence.entities.EmailVerificationToken;
import com.example.authentication.persistence.entities.User;
import com.example.authentication.persistence.repositories.EmailVerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailVerificationTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerificationTokenService.class);
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    @Value("${app.token.email.verification.duration}")
    private Long emailVerificationTokenExpiryDuration;
    @Autowired
    public EmailVerificationTokenService(EmailVerificationTokenRepository emailVerificationTokenRepository){
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    public void createVerificationToken(User user,String token){
        EmailVerificationToken emailVerificationToken = EmailVerificationToken.builder()
                .token(token)
                .tokenStatus(TokenStatus.STATUS_PENDING)
                .user(user)
                .expiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration))
                .build();
        LOGGER.info("Generated Email verification token [" + emailVerificationToken + "]");
        emailVerificationTokenRepository.save(emailVerificationToken);
    }

    public EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) {
        existingToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        existingToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));
        LOGGER.info("Updated Email verification token [" + existingToken + "]");
        return save(existingToken);
    }

    public Optional<EmailVerificationToken> findByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token);
    }

    public EmailVerificationToken save(EmailVerificationToken emailVerificationToken) {
        return emailVerificationTokenRepository.save(emailVerificationToken);
    }

    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    public void verifyExpiration(EmailVerificationToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new InvalidTokenRequestException("Email Verification Token", token.getToken(), "Expired token. Please issue a new request");
        }
    }
}
