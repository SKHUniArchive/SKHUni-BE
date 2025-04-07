package com.skhuni.skhunibackend.member.api;

import com.skhuni.skhunibackend.global.annotation.AuthenticatedEmail;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import com.skhuni.skhunibackend.member.api.request.MemberInfoUpdateReqDto;
import com.skhuni.skhunibackend.member.api.response.MemberInfoResDto;
import com.skhuni.skhunibackend.member.api.response.MembersResDto;
import com.skhuni.skhunibackend.member.domain.EnrollmentStatus;
import com.skhuni.skhunibackend.member.domain.FieldType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface MemberControllerDocs {

    @Operation(summary = "사용자 권한 조회", description = "사용자 권한을 조회합니다. ROLE_USER(로그인 O + 이메일인증 X), ROLE_STUDENT(로그인 O + 이메일인증 O) 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<String> getMemberRole(@AuthenticatedEmail String email);

    @Operation(summary = "사용자 리스트 조회", description = "사용자 리스트를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<MembersResDto> getMembers(@AuthenticatedEmail String email,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) FieldType field,
                                          @RequestParam(required = false) EnrollmentStatus enrollmentStatus,
                                          @RequestParam(required = false) boolean coffeeChat,
                                          @RequestParam(required = false) boolean codeReview,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<MemberInfoResDto> getInfo(@AuthenticatedEmail String email);

    @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<Void> updateInfo(@AuthenticatedEmail String email,
                                 @RequestBody MemberInfoUpdateReqDto memberInfoUpdateReqDto);

}
