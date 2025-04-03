package com.skhuni.skhunibackend.email.application;

import com.skhuni.skhunibackend.email.exception.InvalidEmailAddressException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProdEmailService implements EmailService {

    private static final String EMAIL_FORMAT = "^[a-zA-Z0-9._%+-]+@office\\.skhu\\.ac\\.kr$";

    private final JavaMailSender emailSender;
    private String authNum;

    // TODO(): 이메일 인증은 redis에 저장된 인증번호를 가져와서 인증하는 메소드.


    // TODO(): 이메일 인증 번호 redis에 저장하는 코드 추가
    @Override
    public void sendEmail(String email) throws MessagingException {
        MimeMessage emailForm = createEmailForm(email);
        emailSender.send(emailForm);
    }

    private void createCode() {
        SecureRandom random = new SecureRandom();
        char[] key = new char[6];
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = characters.length();

        for (int i = 0; i < 6; i++) {
            key[i] = characters.charAt(random.nextInt(length));
        }

        authNum = new String(key);
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {
        createCode();
        String setFrom = "SKHUni";
        String title = "SKHUni 재학생 인증 메일";

        validateEmail(email);

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(title);

        String msgOfEmail = mailContents();

        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }

    private void validateEmail(String email) {
        if (!email.matches(EMAIL_FORMAT)) {
            throw new InvalidEmailAddressException();
        }
    }

    private String mailContents() {
        return "<div style='margin:20px;'>" +
                "<h1> 👋🏻 SKHUni 재학생 인증 메일 </h1><br>" +
                "<p>SKHUni는 성공회대학교 이메일로 인증해야만 사용할 수 있는 서비스로, </p>" +
                "<p>성공회대 office 365 메일로 재학생 인증 후 사용하실 수 있습니다. </p><br>" +
                "<p>아래의 코드를 인증 코드란에 적고 재학생 인증을 마쳐주세요.<p><br>" +
                "<div align='center' style='border:1px solid black; font-family:verdana';>" +
                "<div style='font-size:130%'>" +
                "<strong><br>" +
                authNum +
                "</strong><div><br/> " +
                "</div>";
    }

}
