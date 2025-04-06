package com.skhuni.skhunibackend.member.domain;

public enum Role {
    ROLE_GUEST("비회원"),
    ROLE_USER("일반 유저"),
    ROLE_STUDENT("학교 학생"),
    ROLE_ADMIN("어드민");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
