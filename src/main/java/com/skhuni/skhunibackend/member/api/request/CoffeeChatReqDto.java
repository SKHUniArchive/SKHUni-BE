package com.skhuni.skhunibackend.member.api.request;

public record CoffeeChatReqDto(
        Long toMemberId,
        String content
) {
}
