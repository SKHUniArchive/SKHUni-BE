package com.skhuni.skhunibackend.project.exception;

import com.skhuni.skhunibackend.global.error.exception.NotFoundGroupException;

public class ProjectNotFoundException extends NotFoundGroupException {
    public ProjectNotFoundException(String message) {
        super(message);
    }

    public ProjectNotFoundException() {
        this("존재하지 않는 프로젝트 입니다.");
    }
}
