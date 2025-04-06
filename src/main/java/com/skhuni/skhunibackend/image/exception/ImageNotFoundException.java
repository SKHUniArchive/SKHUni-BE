package com.skhuni.skhunibackend.image.exception;

import com.skhuni.skhunibackend.global.error.exception.NotFoundGroupException;

public class ImageNotFoundException extends NotFoundGroupException {
    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException() {
        this("존재하지 않는 이미지 입니다.");
    }
}
