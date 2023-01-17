package com.microservice.notification;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
public record NotificationController(NotificationService notificationService) {

    @PostMapping(path ="/send")
    public boolean sendNotification(@RequestBody NotificationRequest notificationRequest){

        return notificationService.sendNotification(notificationRequest);

    }
}
