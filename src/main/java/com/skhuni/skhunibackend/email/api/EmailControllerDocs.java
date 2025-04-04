package com.skhuni.skhunibackend.email.api;

import com.skhuni.skhunibackend.email.api.request.EmailAuthCodeCheckReqDto;
import com.skhuni.skhunibackend.email.api.request.EmailCheckReqDto;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailControllerDocs {

    @Operation(summary = "이메일 인증 코드 전송", description = "이메일 인증 코드 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    RspTemplate<Void> email(@RequestBody EmailCheckReqDto emailCheckReqDto)
            throws MessagingException, UnsupportedEncodingException;

    @Operation(summary = "이메일 인증 코드 검증", description = "이메일 인증 코드 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    RspTemplate<Void> verifyAuthCode(@RequestBody EmailAuthCodeCheckReqDto emailAuthCodeCheckReqDto);

}