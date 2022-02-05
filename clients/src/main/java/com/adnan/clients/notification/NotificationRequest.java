package com.adnan.clients.notification;

import java.time.LocalDateTime;

public record NotificationRequest(Integer toCustomerId, String toCustomerEmail,
                                  String sender,
                                  String message,
                                  LocalDateTime sendAt) {
}
