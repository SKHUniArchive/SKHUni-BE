package com.skhuni.skhunibackend.member.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "분야. PLANNING(기획), DESIGN(디자인), FRONTEND(프론트엔드), BACKEND(백엔드), AI(AI)")
public enum FieldType {
    PLANNING("기획"),
    DESIGN("디자인"),
    FRONTEND("프론트엔드"),
    BACKEND("백엔드"),
    AI("AI");

    private final String description;

    FieldType(String description) {
        this.description = description;
    }
}