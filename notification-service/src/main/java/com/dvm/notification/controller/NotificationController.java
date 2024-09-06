package com.dvm.notification.controller;

import com.dvm.notification.dto.request.Recipient;
import com.dvm.notification.dto.request.SendEmailRequest;
import com.dvm.event.dto.NotificationEvent;
import com.dvm.notification.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    EmailService emailService;

    @KafkaListener(topics = "notification-create-user")
    public void listenCreateUser(NotificationEvent event) {
        List<Recipient> recipients = new ArrayList<>();
        recipients.add(Recipient.builder()
                .email(event.getRecipient())
                .build());
        emailService.sendMail(SendEmailRequest.builder()
                        .to(recipients)
                        .subject(event.getSubject())
                        .htmlContent(event.getBody())
                .build());
    }
}
