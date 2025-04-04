package com.skhuni.skhunibackend.auth.exception;

import com.skhuni.skhunibackend.global.error.exception.NotFoundGroupException;

public class NotFoundGithubEmailException extends NotFoundGroupException {
    public NotFoundGithubEmailException(String message) {
        super(message);
    }

    public NotFoundGithubEmailException() {
        this("Github 이메일이 존재하지 않습니다. 다른 플랫폼을 이용한 로그인 혹은 Github 이메일을 등록 후 시도해주세요.");
    }
}
