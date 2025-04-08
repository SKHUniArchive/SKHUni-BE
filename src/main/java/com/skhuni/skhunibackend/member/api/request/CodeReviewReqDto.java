package com.skhuni.skhunibackend.member.api.request;

public record CodeReviewReqDto(
        Long toMemberId,
        String githubLink,
        String content
) {
}
