package com.skhuni.skhunibackend.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}