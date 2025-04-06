package com.skhuni.skhunibackend.image.exception;

import com.skhuni.skhunibackend.global.error.exception.InvalidGroupException;

public class ImageAlreadyUploadedException extends InvalidGroupException {
    public ImageAlreadyUploadedException(String message) {
        super(message);
    }

    public ImageAlreadyUploadedException() {
        this("이미 이미지를 업로드했습니다.");
    }
}
