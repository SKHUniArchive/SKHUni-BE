package com.skhuni.skhunibackend.email.exception;

import com.skhuni.skhunibackend.global.error.exception.InvalidGroupException;

public class InvalidEmailAddressException extends InvalidGroupException {

    public InvalidEmailAddressException(String message) {
        super(message);
    }

    public InvalidEmailAddressException() {
        this("이메일 형식이 올바르지 않습니다.");
    }
}
