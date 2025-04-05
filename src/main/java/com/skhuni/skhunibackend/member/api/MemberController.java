package com.skhuni.skhunibackend.member.api;

import com.skhuni.skhunibackend.global.annotation.AuthenticatedEmail;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import com.skhuni.skhunibackend.member.api.request.MemberInfoUpdateReqDto;
import com.skhuni.skhunibackend.member.api.response.MemberInfoResDto;
import com.skhuni.skhunibackend.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    @GetMapping("/me")
    public RspTemplate<MemberInfoResDto> getInfo(@AuthenticatedEmail String email) {
        MemberInfoResDto info = memberService.getInfo(email);
        return RspTemplate.OK(info);
    }

    @PostMapping
    public RspTemplate<Void> updateInfo(@AuthenticatedEmail String email,
                                        @RequestBody MemberInfoUpdateReqDto memberInfoUpdateReqDto) {
        memberService.updateInfo(email, memberInfoUpdateReqDto);
        return RspTemplate.OK();
    }

}
