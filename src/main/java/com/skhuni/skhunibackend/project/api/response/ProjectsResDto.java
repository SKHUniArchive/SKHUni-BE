package com.skhuni.skhunibackend.project.api.response;

import com.skhuni.skhunibackend.global.dto.PageInfoResDto;
import java.util.List;
import lombok.Builder;

@Builder
public record ProjectsResDto(
        List<ProjectInfoResDto> projects,
        PageInfoResDto pageInfo
) {
    public static ProjectsResDto of(List<ProjectInfoResDto> projects, PageInfoResDto pageInfoResDto) {
        return ProjectsResDto.builder()
                .projects(projects)
                .pageInfo(pageInfoResDto)
                .build();
    }

}
