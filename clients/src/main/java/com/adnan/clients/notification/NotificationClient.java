package com.adnan.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient("notification")
@FeignClient(
        name = "notification",
        url = "${clients.notification.url}"
)
public interface NotificationClient {
    @PostMapping(value = "api/v1/notification")
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) ;
}
