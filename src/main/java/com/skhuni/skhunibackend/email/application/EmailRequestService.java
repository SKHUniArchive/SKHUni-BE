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

        String confirmationContent = createCoffeeChatConfirmationContent(toMember, content);
        sendEmail(fromMember.getContactEmail(), "[확인] 커피챗 요청이 정상적으로 전송되었습니다", confirmationContent);
    }

    @Transactional
    public void sendCodeReviewRequest(String fromEmail, Long toMemberId, String githubLink, String content)
            throws MessagingException {
        Member fromMember = memberRepository.findByEmail(fromEmail).orElseThrow(MemberNotFoundException::new);
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(MemberNotFoundException::new);

        String emailContent = createCodeReviewContent(fromMember, githubLink, content);
        sendEmail(toMember.getContactEmail(), "코드리뷰 요청", emailContent);

        String confirmationContent = createCodeReviewConfirmationContent(toMember, githubLink, content);
        sendEmail(fromMember.getContactEmail(), "[확인] 코드리뷰 요청이 정상적으로 전송되었습니다", confirmationContent);
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

        return """
                <div style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #ffffff; padding: 30px; border-radius: 10px; border: 1px solid #e0e0e0; max-width: 600px; margin: 0 auto; color: #333;">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h2 style="margin: 0; color: #5C67F2;">☕ 커피챗 요청</h2>
                        <p style="font-size: 14px; color: #888;">커피 한 잔과 함께 대화를 시작해보세요!</p>
                    </div>
                    <div style="font-size: 15px; line-height: 1.8;">
                        <p><strong>👤 신청인:</strong>\s""" + member.getName() + """
                </p>
                <p><strong>📧 이메일:</strong>\s""" + member.getContactEmail() + """
                </p>
                <p><strong>🕒 신청 시간:</strong>\s""" + formattedDate + """
                </p>
                <p><strong>💬 요청 내용:</strong></p>
                <div style="background-color: #f8f9fa; padding: 15px; border: 1px solid #ddd; border-radius: 5px; white-space: pre-wrap;">
                """ + content + """
                        </div>
                    </div>
                    <div style="text-align: center; margin-top: 40px; font-size: 13px; color: #aaa;">
                        본 메일은 커피챗 요청 시스템을 통해 자동 생성되었습니다.
                    </div>
                </div>
                """;
    }

    private String createCodeReviewContent(Member member, String githubLink, String content) {
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

        return """
                <div style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #ffffff; padding: 30px; border-radius: 10px; border: 1px solid #e0e0e0; max-width: 600px; margin: 0 auto; color: #333;">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h2 style="margin: 0; color: #2196F3;">💻 코드리뷰 요청</h2>
                        <p style="font-size: 14px; color: #888;">더 나은 코드를 위해 리뷰를 요청드려요!</p>
                    </div>
                    <div style="font-size: 15px; line-height: 1.8;">
                        <p><strong>👤 신청인:</strong>\s""" + member.getName() + """
                </p>
                <p><strong>📧 이메일:</strong>\s""" + member.getContactEmail() + """
                </p>
                <p><strong>🕒 신청 시간:</strong>\s""" + formattedDate + """
                </p>
                <p><strong>🔗 GitHub 링크:</strong> <a href='""" + githubLink
                + """
                ' style='color: #2196F3; text-decoration: none;'>""" + githubLink + """
                </a></p>
                <p><strong>💬 요청 내용:</strong></p>
                <div style="background-color: #f8f9fa; padding: 15px; border: 1px solid #ddd; border-radius: 5px; white-space: pre-wrap;">
                """ + content + """
                        </div>
                    </div>
                    <div style="text-align: center; margin-top: 40px; font-size: 13px; color: #aaa;">
                        본 메일은 코드리뷰 요청 시스템을 통해 자동 생성되었습니다.
                    </div>
                </div>
                """;
    }

    private String createCoffeeChatConfirmationContent(Member receiver, String content) {
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

        return """
                <div style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #ffffff; padding: 30px; border-radius: 10px; border: 1px solid #e0e0e0; max-width: 600px; margin: 0 auto; color: #333;">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h2 style="margin: 0; color: #4CAF50;">✅ 커피챗 요청이 전송되었습니다</h2>
                        <p style="font-size: 14px; color: #888;">요청 내용은 아래와 같습니다.</p>
                    </div>
                    <div style="font-size: 15px; line-height: 1.8;">
                        <p><strong>📤 보낸 대상:</strong>\s""" + receiver.getName() + " (" + receiver.getContactEmail()
                + ")</p>" + """
                <p><strong>🕒 보낸 시간:</strong>\s""" + formattedDate + """
                </p>
                <p><strong>💬 요청 내용:</strong></p>
                <div style="background-color: #f8f9fa; padding: 15px; border: 1px solid #ddd; border-radius: 5px; white-space: pre-wrap;">
                """ + content + """
                        </div>
                    </div>
                    <div style="text-align: center; margin-top: 40px; font-size: 13px; color: #aaa;">
                        본 메일은 커피챗 요청 확인을 위해 자동 발송되었습니다.
                    </div>
                </div>
                """;
    }

    private String createCodeReviewConfirmationContent(Member receiver, String githubLink, String content) {
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

        return """
                <div style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #ffffff; padding: 30px; border-radius: 10px; border: 1px solid #e0e0e0; max-width: 600px; margin: 0 auto; color: #333;">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h2 style="margin: 0; color: #2196F3;">✅ 코드리뷰 요청이 전송되었습니다</h2>
                        <p style="font-size: 14px; color: #888;">요청 내용은 아래와 같습니다.</p>
                    </div>
                    <div style="font-size: 15px; line-height: 1.8;">
                        <p><strong>📤 보낸 대상:</strong>\s""" + receiver.getName() + " (" + receiver.getContactEmail()
                + ")</p>" + """
                <p><strong>🕒 보낸 시간:</strong>\s""" + formattedDate + """
                </p>
                <p><strong>🔗 GitHub 링크:</strong> <a href='""" + githubLink + "' style='color: #2196F3;'>" + githubLink
                + "</a></p>" + """
                <p><strong>💬 요청 내용:</strong></p>
                <div style="background-color: #f8f9fa; padding: 15px; border: 1px solid #ddd; border-radius: 5px; white-space: pre-wrap;">
                """ + content + """
                        </div>
                    </div>
                    <div style="text-align: center; margin-top: 40px; font-size: 13px; color: #aaa;">
                        본 메일은 코드리뷰 요청 확인을 위해 자동 발송되었습니다.
                    </div>
                </div>
                """;
    }
}