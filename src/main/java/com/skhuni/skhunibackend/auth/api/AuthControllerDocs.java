package com.skhuni.skhunibackend.auth.api;

import com.skhuni.skhunibackend.auth.api.dto.request.RefreshTokenReqDto;
import com.skhuni.skhunibackend.auth.api.dto.request.TokenReqDto;
import com.skhuni.skhunibackend.global.jwt.api.dto.TokenDto;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDocs {

    @Operation(summary = "자체 AccessToken, RefreshToken 발급", description = "code를 이용하여 액세스, 리프레쉬 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 발급 성공")
    })
    RspTemplate<TokenDto> generateAccessAndRefreshToken(
            @Parameter(name = "provider", description = "소셜 타입(google, kakao)", in = ParameterIn.PATH)
            @PathVariable(name = "provider") String provider,
            @RequestBody TokenReqDto tokenReqDto);

    @Operation(summary = "액세스 토큰 재발급", description = "리프레쉬 토큰으로 액세스 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 발급 성공")
    })
    RspTemplate<TokenDto> generateAccessToken(@RequestBody RefreshTokenReqDto refreshTokenReqDto);
}
