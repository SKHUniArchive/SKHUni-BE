package com.skhuni.skhunibackend.member.api.response;

import com.skhuni.skhunibackend.project.api.response.ProjectsResDto;
import lombok.Builder;

@Builder
public record MemberDetailInfoResDto(
        MemberInfoResDto members,
        ProjectsResDto projects
) {
    public static MemberDetailInfoResDto of(MemberInfoResDto members, ProjectsResDto projects) {
        return MemberDetailInfoResDto.builder()
                .members(members)
                .projects(projects)
                .build();
    }
}
