package com.skhuni.skhunibackend.project.domain.repository;

import com.skhuni.skhunibackend.project.domain.Project;
import org.springframework.data.domain.Page;

public interface ProjectCustomRepository {

    Page<Project> findAllByMyMemberEmail(String email, int page, int size);

    Page<Project> findAllByMemberMemberId(Long memberId, int page, int size);

    Page<Project> findAll(int page, int size);

}
