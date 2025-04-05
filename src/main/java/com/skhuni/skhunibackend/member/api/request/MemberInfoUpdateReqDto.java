package com.skhuni.skhunibackend.member.api.request;

import com.skhuni.skhunibackend.member.domain.EnrollmentStatus;
import com.skhuni.skhunibackend.member.domain.FieldType;

public record MemberInfoUpdateReqDto(
        String name,
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
}