package com.skhuni.skhunibackend.email.api;

import com.skhuni.skhunibackend.email.api.request.EmailCheckReqDto;
import com.skhuni.skhunibackend.email.application.EmailService;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController implements EmailControllerDocs {

    private final EmailService emailService;

    @PostMapping("/email-check")
    public RspTemplate<Void> emailCheck(@RequestBody EmailCheckReqDto emailCheckReqDto)
            throws MessagingException, UnsupportedEncodingException {
        emailService.sendEmail(emailCheckReqDto.email());
        return RspTemplate.OK();
    }

}