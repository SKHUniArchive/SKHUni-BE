package com.skhuni.skhunibackend.auth.api;

import com.skhuni.skhunibackend.auth.api.dto.request.RefreshTokenReqDto;
import com.skhuni.skhunibackend.auth.api.dto.request.TokenReqDto;
import com.skhuni.skhunibackend.auth.api.dto.response.MemberLoginResDto;
import com.skhuni.skhunibackend.auth.api.dto.response.UserInfo;
import com.skhuni.skhunibackend.auth.application.AuthMemberService;
import com.skhuni.skhunibackend.auth.application.AuthService;
import com.skhuni.skhunibackend.auth.application.AuthServiceFactory;
import com.skhuni.skhunibackend.auth.application.TokenService;
import com.skhuni.skhunibackend.global.jwt.api.dto.TokenDto;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import com.skhuni.skhunibackend.member.domain.SocialType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthServiceFactory authServiceFactory;
    private final AuthMemberService memberService;
    private final TokenService tokenService;

    @PostMapping("/{provider}/token")
    public RspTemplate<TokenDto> generateAccessAndRefreshToken(
            @Parameter(name = "provider", description = "소셜 타입(google, kakao)", in = ParameterIn.PATH)
            @PathVariable(name = "provider") String provider,
            @RequestBody TokenReqDto tokenReqDto) {
        AuthService authService = authServiceFactory.getAuthService(provider);
        UserInfo userInfo = authService.getUserInfo(tokenReqDto.code());

        MemberLoginResDto getMemberDto = memberService.saveUserInfo(userInfo,
                SocialType.valueOf(provider.toUpperCase()));
        TokenDto getToken = tokenService.getToken(getMemberDto);

        return new RspTemplate<>(HttpStatus.OK, "토큰 발급", getToken);
    }

    @PostMapping("/token/access")
    public RspTemplate<TokenDto> generateAccessToken(@RequestBody RefreshTokenReqDto refreshTokenReqDto) {
        TokenDto getToken = tokenService.generateAccessToken(refreshTokenReqDto);

        return new RspTemplate<>(HttpStatus.OK, "액세스 토큰 발급", getToken);
    }

}
