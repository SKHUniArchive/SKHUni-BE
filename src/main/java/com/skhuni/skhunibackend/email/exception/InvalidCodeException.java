package com.skhuni.skhunibackend.email.exception;

import com.skhuni.skhunibackend.global.error.exception.InvalidGroupException;

public class InvalidCodeException extends InvalidGroupException {

    public InvalidCodeException(String message) {
        super(message);
    }
    
}
