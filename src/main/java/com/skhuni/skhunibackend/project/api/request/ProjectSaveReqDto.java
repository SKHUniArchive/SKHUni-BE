package com.skhuni.skhunibackend.project.api.request;

public record ProjectSaveReqDto(
        String title,
        String picture,
        String introLine,
        String introduction,
        String githubLink1,
        String githubLink2,
        String siteLink
) {
}
