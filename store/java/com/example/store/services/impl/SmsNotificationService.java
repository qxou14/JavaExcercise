package com.example.store.services.impl;

import com.example.store.services.NotificationService;
import org.springframework.stereotype.Service;

@Service("sms")
public class SmsNotificationService implements NotificationService {
    @Override
    public String send(String message) {
        return "SMS";
    }
}
