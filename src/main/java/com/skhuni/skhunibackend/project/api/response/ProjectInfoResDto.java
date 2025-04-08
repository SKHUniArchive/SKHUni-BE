package com.skhuni.skhunibackend.project.api.response;

import com.skhuni.skhunibackend.project.domain.Project;
import lombok.Builder;

@Builder
public record ProjectInfoResDto(
        Long projectId,
        boolean isAuthor,
        String title,
        String picture,
        String introLine,
        String introduction,
        String githubLink1,
        String githubLink2,
        String siteLink
) {
    public static ProjectInfoResDto of(Project project, boolean isAuthor) {
        return ProjectInfoResDto.builder()
                .projectId(project.getId())
                .isAuthor(isAuthor)
                .title(project.getTitle())
                .picture(project.getPicture())
                .introLine(project.getIntroLine())
                .introduction(project.getIntroduction())
                .githubLink1(project.getGithubLink1())
                .githubLink2(project.getGithubLink2())
                .siteLink(project.getSiteLink())
                .build();
    }

    public static ProjectInfoResDto of(Project project) {
        return ProjectInfoResDto.builder()
                .projectId(project.getId())
                .title(project.getTitle())
                .picture(project.getPicture())
                .introLine(project.getIntroLine())
                .introduction(project.getIntroduction())
                .githubLink1(project.getGithubLink1())
                .githubLink2(project.getGithubLink2())
                .siteLink(project.getSiteLink())
                .build();
    }
}
