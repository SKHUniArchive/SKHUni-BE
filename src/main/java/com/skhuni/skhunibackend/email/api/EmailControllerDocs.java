package com.skhuni.skhunibackend.email.api;

import com.skhuni.skhunibackend.email.api.request.EmailCheckReqDto;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailControllerDocs {

    @Operation(
            summary = "이메일 인증",
            description = "이메일 인증 코드 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    RspTemplate<Void> emailCheck(@RequestBody EmailCheckReqDto emailCheckReqDto)
            throws MessagingException, UnsupportedEncodingException;

}