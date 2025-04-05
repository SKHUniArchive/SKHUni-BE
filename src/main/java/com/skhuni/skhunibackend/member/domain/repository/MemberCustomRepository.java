package com.skhuni.skhunibackend.member.domain.repository;

import com.skhuni.skhunibackend.member.domain.EnrollmentStatus;
import com.skhuni.skhunibackend.member.domain.FieldType;
import com.skhuni.skhunibackend.member.domain.Member;
import org.springframework.data.domain.Page;

public interface MemberCustomRepository {

    Page<Member> searchMembers(
            String email,
            String name,
            FieldType field,
            EnrollmentStatus enrollmentStatus,
            boolean coffeeChat,
            boolean codeReview,
            int page,
            int size
    );

}
