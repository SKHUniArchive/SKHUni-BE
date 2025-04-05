package com.skhuni.skhunibackend.image.api;

import com.skhuni.skhunibackend.global.annotation.AuthenticatedEmail;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface ImageControllerDocs {

    @Operation(summary = "프로필 이미지 업로드", description = "프로필 이미지 업로드 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업로드 성공"),
            @ApiResponse(responseCode = "401", description = "인증실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    RspTemplate<Void> imageProfileUpload(@AuthenticatedEmail String email,
                                         @RequestPart("multipartFile") MultipartFile multipartFile)
            throws IOException;
}
