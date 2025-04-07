package com.skhuni.skhunibackend.member.api.response;

import com.skhuni.skhunibackend.member.domain.EnrollmentStatus;
import com.skhuni.skhunibackend.member.domain.FieldType;
import com.skhuni.skhunibackend.member.domain.Member;
import com.skhuni.skhunibackend.member.domain.MemberLink;
import lombok.Builder;

@Builder
public record MemberInfoResDto(
        Long memberId,
        String email,
        String name,
        String picture,
        String contactEmail,
        String studentId,
        String introLine,
        String introduction,
        EnrollmentStatus enrollmentStatus,
        FieldType fieldType,
        String techStack,
        boolean coffeeChatOpen,
        boolean codeReviewOpen,
        String notion,
        String github,
        String linkedIn,
        String etc1,
        String etc2
) {
    public static MemberInfoResDto of(Member member, MemberLink memberLink) {
        return MemberInfoResDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .picture(member.getPicture())
                .contactEmail(member.getContactEmail())
                .studentId(member.getStudentId())
                .introLine(member.getIntroLine())
                .introduction(member.getIntroduction())
                .enrollmentStatus(member.getEnrollmentStatus())
                .fieldType(member.getFieldType())
                .techStack(member.getTechStack())
                .coffeeChatOpen(member.isCoffeeChatOpen())
                .codeReviewOpen(member.isCodeReviewOpen())
                .notion(memberLink.getNotion())
                .github(memberLink.getGithub())
                .linkedIn(memberLink.getLinkedIn())
                .etc1(memberLink.getEtc1())
                .etc2(memberLink.getEtc2())
                .build();
    }
}
