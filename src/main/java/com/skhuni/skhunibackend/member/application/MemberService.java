package com.skhuni.skhunibackend.member.application;

import com.skhuni.skhunibackend.member.api.request.MemberInfoUpdateReqDto;
import com.skhuni.skhunibackend.member.api.response.MemberInfoResDto;
import com.skhuni.skhunibackend.member.domain.Member;
import com.skhuni.skhunibackend.member.domain.MemberLink;
import com.skhuni.skhunibackend.member.domain.repository.MemberLinkRepository;
import com.skhuni.skhunibackend.member.domain.repository.MemberRepository;
import com.skhuni.skhunibackend.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberLinkRepository memberLinkRepository;

    public MemberInfoResDto getInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        MemberLink memberLink = memberLinkRepository.findByMemberId(member.getId());

        return MemberInfoResDto.of(member, memberLink);
    }

    @Transactional
    public void updateInfo(String email, MemberInfoUpdateReqDto memberInfoUpdateReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        MemberLink memberLink = memberLinkRepository.findByMemberId(member.getId());

        member.updateInfo(memberInfoUpdateReqDto.name(),
                memberInfoUpdateReqDto.contactEmail(),
                memberInfoUpdateReqDto.studentId(),
                memberInfoUpdateReqDto.introLine(),
                memberInfoUpdateReqDto.introduction(),
                memberInfoUpdateReqDto.enrollmentStatus(),
                memberInfoUpdateReqDto.fieldType(),
                memberInfoUpdateReqDto.techStack(),
                memberInfoUpdateReqDto.coffeeChatOpen(),
                memberInfoUpdateReqDto.codeReviewOpen());
        memberLink.updateLinks(memberInfoUpdateReqDto.notion(),
                memberInfoUpdateReqDto.github(),
                memberInfoUpdateReqDto.linkedIn(),
                memberInfoUpdateReqDto.etc1(),
                memberInfoUpdateReqDto.etc2());
    }

}
