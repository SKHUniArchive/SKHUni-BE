package com.skhuni.skhunibackend.member.api;

import com.skhuni.skhunibackend.email.application.EmailRequestService;
import com.skhuni.skhunibackend.global.annotation.AuthenticatedEmail;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import com.skhuni.skhunibackend.member.api.request.CodeReviewReqDto;
import com.skhuni.skhunibackend.member.api.request.CoffeeChatReqDto;
import com.skhuni.skhunibackend.member.api.request.MemberInfoUpdateReqDto;
import com.skhuni.skhunibackend.member.api.response.MemberDetailInfoResDto;
import com.skhuni.skhunibackend.member.api.response.MemberInfoResDto;
import com.skhuni.skhunibackend.member.api.response.MembersResDto;
import com.skhuni.skhunibackend.member.application.MemberService;
import com.skhuni.skhunibackend.member.domain.EnrollmentStatus;
import com.skhuni.skhunibackend.member.domain.FieldType;
import com.skhuni.skhunibackend.project.api.response.ProjectsResDto;
import com.skhuni.skhunibackend.project.application.ProjectService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;
    private final ProjectService projectService;
    private final EmailRequestService emailRequestService;

    @GetMapping("/role")
    public RspTemplate<String> getMemberRole(@AuthenticatedEmail String email) {
        String role = memberService.getMemberRole(email);
        return RspTemplate.OK("사용자 권한 반환", role);
    }

    @GetMapping
    public RspTemplate<MembersResDto> getMembers(@AuthenticatedEmail String email,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) FieldType field,
                                                 @RequestParam(required = false) EnrollmentStatus enrollmentStatus,
                                                 @RequestParam(required = false) boolean coffeeChat,
                                                 @RequestParam(required = false) boolean codeReview,
                                                 @RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        MembersResDto members = memberService
                .getMembers(email, name, field, enrollmentStatus, coffeeChat, codeReview, page, size);
        return RspTemplate.OK(members);
    }

    @GetMapping("/me")
    public RspTemplate<MemberInfoResDto> getInfo(@AuthenticatedEmail String email) {
        MemberInfoResDto info = memberService.getInfo(email);
        return RspTemplate.OK(info);
    }

    @GetMapping("/{memberId}")
    public RspTemplate<MemberDetailInfoResDto> getMemberInfo(@PathVariable Long memberId,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        MemberInfoResDto info = memberService.getMemberInfo(memberId);
        ProjectsResDto projects = projectService.getMemberProjects(memberId, page, size);
        MemberDetailInfoResDto memberDetailInfoResDto = MemberDetailInfoResDto.of(info, projects);
        return RspTemplate.OK(memberDetailInfoResDto);
    }

    @PostMapping
    public RspTemplate<Void> updateInfo(@AuthenticatedEmail String email,
                                        @RequestBody MemberInfoUpdateReqDto memberInfoUpdateReqDto) {
        memberService.updateInfo(email, memberInfoUpdateReqDto);
        return RspTemplate.OK();
    }

    @PostMapping("/request/coffee-chat")
    public RspTemplate<Void> requestCoffeeChat(@AuthenticatedEmail String email,
                                               @RequestBody CoffeeChatReqDto coffeeChatReqDto)
            throws MessagingException {
        emailRequestService.sendCoffeeChatRequest(
                email,
                coffeeChatReqDto.toMemberId(),
                coffeeChatReqDto.content());
        return RspTemplate.OK();
    }

    @PostMapping("/request/code-review")
    public RspTemplate<Void> requestCodeReview(@AuthenticatedEmail String email,
                                               @RequestBody CodeReviewReqDto codeReviewReqDto)
            throws MessagingException {
        emailRequestService.sendCodeReviewRequest(
                email,
                codeReviewReqDto.toMemberId(),
                codeReviewReqDto.githubLink(),
                codeReviewReqDto.content());
        return RspTemplate.OK();
    }

}
