package com.skhuni.skhunibackend.member.application;

import com.skhuni.skhunibackend.global.dto.PageInfoResDto;
import com.skhuni.skhunibackend.member.api.request.MemberInfoUpdateReqDto;
import com.skhuni.skhunibackend.member.api.response.MemberInfoResDto;
import com.skhuni.skhunibackend.member.api.response.MembersResDto;
import com.skhuni.skhunibackend.member.domain.EnrollmentStatus;
import com.skhuni.skhunibackend.member.domain.FieldType;
import com.skhuni.skhunibackend.member.domain.Member;
import com.skhuni.skhunibackend.member.domain.MemberLink;
import com.skhuni.skhunibackend.member.domain.repository.MemberLinkRepository;
import com.skhuni.skhunibackend.member.domain.repository.MemberRepository;
import com.skhuni.skhunibackend.member.exception.MemberNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberLinkRepository memberLinkRepository;

    public MembersResDto getMembers(String email, String name, FieldType field,
                                    EnrollmentStatus enrollmentStatus, boolean coffeeChat,
                                    boolean codeReview, int page, int size) {
        Page<Member> members = memberRepository
                .searchMembers(email, name, field, enrollmentStatus, coffeeChat, codeReview, page, size);

        List<MemberInfoResDto> memberInfoResDtos = members.getContent()
                .stream()
                .map(m -> {
                    MemberLink memberLink = memberLinkRepository.findByMemberId(m.getId())
                            .orElseThrow(MemberNotFoundException::new);
                    return MemberInfoResDto.of(m, memberLink);
                })
                .toList();

        return MembersResDto.from(memberInfoResDtos, PageInfoResDto.from(members));
    }

    public MemberInfoResDto getInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        MemberLink memberLink = memberLinkRepository.findByMemberId(member.getId())
                .orElseThrow(MemberNotFoundException::new);

        return MemberInfoResDto.of(member, memberLink);
    }

    public MemberInfoResDto getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        MemberLink memberLink = memberLinkRepository.findByMemberId(member.getId())
                .orElseThrow(MemberNotFoundException::new);

        return MemberInfoResDto.of(member, memberLink);
    }

    @Transactional
    public void updateInfo(String email, MemberInfoUpdateReqDto memberInfoUpdateReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        MemberLink memberLink = memberLinkRepository.findByMemberId(member.getId())
                .orElseThrow(MemberNotFoundException::new);

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
