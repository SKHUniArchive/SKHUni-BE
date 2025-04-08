package com.skhuni.skhunibackend.project.api;

import com.skhuni.skhunibackend.global.annotation.AuthenticatedEmail;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import com.skhuni.skhunibackend.project.api.request.ProjectSaveReqDto;
import com.skhuni.skhunibackend.project.api.response.ProjectInfoResDto;
import com.skhuni.skhunibackend.project.api.response.ProjectsResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectControllerDocs {

    @Operation(summary = "프로젝트 저장", description = "프로젝트를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "저장 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<Void> saveProject(@AuthenticatedEmail String email,
                                  @RequestBody ProjectSaveReqDto projectSaveReqDto);

    @Operation(summary = "프로젝트 이미지 업로드", description = "프로젝트 이미지를 업로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업로드 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<String> projectImageUpload(@AuthenticatedEmail String email,
                                           @PathVariable Long projectId,
                                           @RequestPart("multipartFile") MultipartFile multipartFile)
            throws IOException;

    @Operation(summary = "프로젝트 수정", description = "프로젝트를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<Void> updateProject(@AuthenticatedEmail String email,
                                    @PathVariable Long projectId,
                                    @RequestBody ProjectSaveReqDto projectSaveReqDto);

    @Operation(summary = "프로젝트 정보 조회", description = "프로젝트 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<ProjectInfoResDto> getInfo(@AuthenticatedEmail String email,
                                           @PathVariable Long projectId);

    @Operation(summary = "내 프로젝트 목록 조회", description = "내 프로젝트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<ProjectsResDto> getMyProjects(@AuthenticatedEmail String email,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "모든 프로젝트 목록 조회", description = "모든 프로젝트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<ProjectsResDto> getAllProjects(@AuthenticatedEmail String email,
                                               @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "프로젝트 삭제", description = "프로젝트를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<Void> deleteProject(@AuthenticatedEmail String email,
                                    @PathVariable Long projectId);
}
