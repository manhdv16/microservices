package com.dvm.notification.controller;

import com.dvm.notification.dto.request.SendEmailRequest;
import com.dvm.notification.dto.response.ApiResponse;
import com.dvm.notification.dto.response.EmailResponse;
import com.dvm.notification.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    public ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest request) {
        return ApiResponse.<EmailResponse>builder()
                .message("Email sent successfully")
                .data(emailService.sendMail(request))
                .build();
    }

}
