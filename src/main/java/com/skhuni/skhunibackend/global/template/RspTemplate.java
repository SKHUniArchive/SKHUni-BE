package com.skhuni.skhunibackend.global.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 응답 템플릿
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RspTemplate<T> {
    private int statusCode;
    private String message;
    private T data;

    private RspTemplate(HttpStatus httpStatus) {
        this.statusCode = httpStatus.value();
    }

    private RspTemplate(HttpStatus httpStatus, T data) {
        this.statusCode = httpStatus.value();
        this.data = data;
    }

    private RspTemplate(HttpStatus httpStatus, String message) {
        this.statusCode = httpStatus.value();
        this.message = message;
    }

    private RspTemplate(HttpStatus httpStatus, String message, T data) {
        this.statusCode = httpStatus.value();
        this.message = message;
        this.data = data;
    }

    public static <T> RspTemplate<T> OK() {
        return new RspTemplate<>(HttpStatus.OK);
    }

    public static <T> RspTemplate<T> OK(T data) {
        return new RspTemplate<>(HttpStatus.OK, data);
    }

    public static <T> RspTemplate<T> OK(String message, T data) {
        return new RspTemplate<>(HttpStatus.OK, message, data);
    }

}
