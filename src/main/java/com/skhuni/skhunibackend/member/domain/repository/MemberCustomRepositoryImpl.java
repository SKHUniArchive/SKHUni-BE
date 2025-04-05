package com.skhuni.skhunibackend.member.domain.repository;

import static com.skhuni.skhunibackend.member.domain.QMember.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhuni.skhunibackend.member.domain.EnrollmentStatus;
import com.skhuni.skhunibackend.member.domain.FieldType;
import com.skhuni.skhunibackend.member.domain.Member;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MemberCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Member> searchMembers(String email, String name, FieldType field, EnrollmentStatus enrollmentStatus,
                                      boolean coffeeChat, boolean codeReview, int page, int size) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member.email.ne(email));

        if (name != null && !name.isBlank()) {
            builder.and(member.name.containsIgnoreCase(name));
        }

        if (field != null) {
            builder.and(member.fieldType.eq(field));
        }

        if (enrollmentStatus != null) {
            builder.and(member.enrollmentStatus.eq(enrollmentStatus));
        }

        if (coffeeChat) {
            builder.and(member.isCoffeeChatOpen.isTrue());
        }

        if (codeReview) {
            builder.and(member.isCodeReviewOpen.isTrue());
        }

        List<Member> content = queryFactory
                .selectFrom(member)
                .where(builder)
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(member.name.asc())
                .fetch();

        long total = queryFactory
                .select(member.count())
                .from(member)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, PageRequest.of(page - 1, size), total);
    }

}
