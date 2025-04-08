package com.skhuni.skhunibackend.email.application;

import com.skhuni.skhunibackend.member.domain.Member;
import com.skhuni.skhunibackend.member.domain.repository.MemberRepository;
import com.skhuni.skhunibackend.member.exception.MemberNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailRequestService {

    private final MemberRepository memberRepository;
    private final JavaMailSender emailSender;

    @Transactional
    public void sendCoffeeChatRequest(String fromEmail, Long toMemberId, String content) throws MessagingException {
        Member fromMember = memberRepository.findByEmail(fromEmail).orElseThrow(MemberNotFoundException::new);
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(MemberNotFoundException::new);
        String emailContent = createCoffeeChatContent(fromMember, content);
        sendEmail(toMember.getContactEmail(), "커피챗 요청", emailContent);
    }

    @Transactional
    public void sendCodeReviewRequest(String fromEmail, Long toMemberId, String githubLink, String content)
            throws MessagingException {
        Member fromMember = memberRepository.findByEmail(fromEmail).orElseThrow(MemberNotFoundException::new);
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(MemberNotFoundException::new);
        String emailContent = createCodeReviewContent(fromMember, githubLink, content);
        sendEmail(toMember.getContactEmail(), "코드리뷰 요청", emailContent);
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setText(content, "utf-8", "html");
        emailSender.send(message);
    }

    private String createCoffeeChatContent(Member member, String content) {
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        return "<div><p>신청인: " + member.getName() + "</p>" +
                "<p>이메일: " + member.getEmail() + "</p>" +
                "<p>신청시간: " + formattedDate + "</p>" +
                " <p>내용: " + content + "</p></div>";
    }

    private String createCodeReviewContent(Member member, String githubLink, String content) {
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        return "<div><p>신청인: " + member.getName() + "</p>" +
                "<p>이메일: " + member.getEmail() + "</p>" +
                "<p>신청시간: " + formattedDate + "</p>" +
                "<p>github: " + githubLink + "</p>" +
                " <p>내용: " + content + "</p></div>";
    }
}