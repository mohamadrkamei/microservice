package com.microservice.notification;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public record NotificationService(NotificationRepository notificationRepository) {

    public boolean sendNotification(NotificationRequest notificationRequest) {

        notificationRepository.save(Notification.builder()
                .message(notificationRequest.message())
                .sender(notificationRequest.toCustomerEmail())
                .sentAt(LocalDateTime.now())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .toCustomerId(notificationRequest.toCustomerId())
                .build());
        log.info("notification send to customerId :{} , to email {}", notificationRequest.toCustomerId(), notificationRequest.toCustomerEmail());
        return true;

    }
}
