package com.skhuni.skhunibackend.global.error.exception;

public abstract class AuthGroupException extends RuntimeException{
    public AuthGroupException(String message) {
        super(message);
    }
}
