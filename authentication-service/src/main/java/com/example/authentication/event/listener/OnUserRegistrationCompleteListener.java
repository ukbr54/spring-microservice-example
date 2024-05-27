package com.example.authentication.event.listener;

import com.example.authentication.event.OnUserRegistrationCompleteEvent;
import com.example.authentication.exception.MailSendException;
import com.example.authentication.persistence.entities.User;
import com.example.authentication.service.EmailVerificationTokenService;
import com.example.authentication.service.MailService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OnUserRegistrationCompleteListener implements ApplicationListener<OnUserRegistrationCompleteEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnUserRegistrationCompleteListener.class);
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final MailService mailService;
    public OnUserRegistrationCompleteListener(EmailVerificationTokenService emailVerificationTokenService,MailService mailService){
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.mailService = mailService;
    }

    @Async
    @Override
    public void onApplicationEvent(OnUserRegistrationCompleteEvent event) {
        sendEmailVerification(event);
    }

    private void sendEmailVerification(OnUserRegistrationCompleteEvent event){
        User user = event.getUser();
        String token = emailVerificationTokenService.generateNewToken();
        emailVerificationTokenService.createVerificationToken(user,token);

        String recipientAddress = user.getEmail();
        String emailConfirmationUrl = event.getRedirectUrl().queryParam("token",token).toUriString();

        try{
            mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
        }catch (IOException | TemplateException | MessagingException e){
            LOGGER.error(event.toString());
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }
}
