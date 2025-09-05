package com.example.store.services;

import org.springframework.beans.factory.annotation.Qualifier;

public class NotificationManager {

    private final NotificationService notificationService;

    public NotificationManager(@Qualifier("email") NotificationService notificationService){
        this.notificationService = notificationService;
    }

    public void setNotificationService(String message){
        notificationService.send(message);
    }

}
