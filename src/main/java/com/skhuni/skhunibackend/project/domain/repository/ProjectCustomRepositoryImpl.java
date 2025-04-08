package com.skhuni.skhunibackend.project.domain.repository;

import static com.skhuni.skhunibackend.project.domain.QProject.project;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhuni.skhunibackend.project.domain.Project;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ProjectCustomRepositoryImpl implements ProjectCustomRepository {

    private final JPAQueryFactory queryFactory;

    public ProjectCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Project> findAllByMyMemberEmail(String email, int page, int size) {
        List<Project> content = queryFactory
                .selectFrom(project)
                .where(project.member.email.eq(email))
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(project.createdAt.desc())
                .fetch();

        long total = queryFactory
                .select(project)
                .from(project)
                .where(project.member.email.eq(email))
                .stream()
                .count();

        return new PageImpl<>(content, PageRequest.of(page - 1, size), total);
    }

    @Override
    public Page<Project> findAllByMemberMemberId(Long memberId, int page, int size) {
        List<Project> content = queryFactory
                .selectFrom(project)
                .where(project.member.id.eq(memberId))
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(project.createdAt.desc())
                .fetch();

        long total = queryFactory
                .select(project)
                .from(project)
                .where(project.member.id.eq(memberId))
                .stream()
                .count();

        return new PageImpl<>(content, PageRequest.of(page - 1, size), total);
    }

    @Override
    public Page<Project> findAll(int page, int size) {
        List<Project> content = queryFactory
                .selectFrom(project)
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(project.createdAt.desc())
                .fetch();

        long total = queryFactory
                .select(project)
                .from(project)
                .stream()
                .count();

        return new PageImpl<>(content, PageRequest.of(page - 1, size), total);
    }

}
