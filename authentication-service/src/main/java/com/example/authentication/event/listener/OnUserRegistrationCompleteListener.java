package com.example.authentication.event.listener;

import com.example.authentication.event.OnUserRegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnUserRegistrationCompleteListener implements ApplicationListener<OnUserRegistrationCompleteEvent> {
    @Override
    public void onApplicationEvent(OnUserRegistrationCompleteEvent event) {

    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
