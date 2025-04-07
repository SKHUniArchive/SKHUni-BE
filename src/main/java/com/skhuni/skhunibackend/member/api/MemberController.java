package com.skhuni.skhunibackend.member.api;

import com.skhuni.skhunibackend.global.annotation.AuthenticatedEmail;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import com.skhuni.skhunibackend.member.api.request.MemberInfoUpdateReqDto;
import com.skhuni.skhunibackend.member.api.response.MemberInfoResDto;
import com.skhuni.skhunibackend.member.api.response.MembersResDto;
import com.skhuni.skhunibackend.member.application.MemberService;
import com.skhuni.skhunibackend.member.domain.EnrollmentStatus;
import com.skhuni.skhunibackend.member.domain.FieldType;
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
    public RspTemplate<MemberInfoResDto> getMemberInfo(@PathVariable Long memberId) {
        MemberInfoResDto info = memberService.getMemberInfo(memberId);
        return RspTemplate.OK(info);
    }

    @PostMapping
    public RspTemplate<Void> updateInfo(@AuthenticatedEmail String email,
                                        @RequestBody MemberInfoUpdateReqDto memberInfoUpdateReqDto) {
        memberService.updateInfo(email, memberInfoUpdateReqDto);
        return RspTemplate.OK();
    }

}
