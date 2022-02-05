package com.adnan.notification;

import com.adnan.clients.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record NotificationService(NotificationRepository notificationRepository) {
    public void saveNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .sender(notificationRequest.sender())
                .message(notificationRequest.message())
                .sendAt(notificationRequest.sendAt())
                .build();

        log.info("Saving message {} for email {}", notification.getMessage(), notification.getToCustomerEmail() );
        notificationRepository.save(notification);
    }
}
