package com.skhuni.skhunibackend.email.api.request;

public record EmailAuthCodeCheckReqDto(
        String email,
        String authCode
) {
}
