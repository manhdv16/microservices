package com.dvm.notification.service;

import com.dvm.notification.dto.request.EmailRequest;
import com.dvm.notification.dto.request.SendEmailRequest;
import com.dvm.notification.dto.request.Sender;
import com.dvm.notification.dto.response.EmailResponse;
import com.dvm.notification.exception.AppException;
import com.dvm.notification.exception.ErrorCode;
import com.dvm.notification.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmailService {
    EmailClient emailClient;

    @Value("${brevo.api.key}")
    @NonFinal
    String apiKey;

    public EmailResponse sendMail(SendEmailRequest request) {
        EmailRequest emailRequest =
                EmailRequest.builder()
                        .sender(Sender.builder()
                                    .name("admin")
                                    .email("nohope0400@gmail.com")
                                    .build())
                        .to(request.getTo())
                        .subject(request.getSubject())
                        .htmlContent(request.getHtmlContent())
                        .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        }catch (FeignException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
