package com.skhuni.skhunibackend.member.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "학생의 재학 상태. ENROLLED(재학중), LEAVE(휴학중), GRADUATED(졸업), DEFERRED(졸업 유예)")
public enum EnrollmentStatus {

    ENROLLED("재학중"),
    LEAVE("휴학중"),
    GRADUATED("졸업"),
    DEFERRED("졸업 유예");

    private final String description;

    EnrollmentStatus(String description) {
        this.description = description;
    }
}