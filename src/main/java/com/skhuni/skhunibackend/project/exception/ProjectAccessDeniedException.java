package com.skhuni.skhunibackend.project.exception;

import com.skhuni.skhunibackend.global.error.exception.AccessDeniedGroupException;

public class ProjectAccessDeniedException extends AccessDeniedGroupException {
    public ProjectAccessDeniedException(String message) {
        super(message);
    }

    public ProjectAccessDeniedException() {
        this("프로젝트 접근 권한이 없습니다.");
    }

}
