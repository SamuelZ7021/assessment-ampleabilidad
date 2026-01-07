package com.riwi.assessment.infrastructure.adapter.out.notifications;

import com.riwi.assessment.domain.port.out.NotificationPort;
import org.springframework.stereotype.Component;

@Component
public class ConsoleNotificationAdapter implements NotificationPort {
    @Override
    public void notify(String message) {
        System.out.println("[NOTIFICATION] Message: " + message);
    }
}