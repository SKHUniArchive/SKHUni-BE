package com.skhuni.skhunibackend.image.api;

import com.skhuni.skhunibackend.global.annotation.AuthenticatedEmail;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import com.skhuni.skhunibackend.image.application.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController implements ImageControllerDocs {

    private final ImageService imageService;

    @PostMapping("/upload")
    public RspTemplate<String> imageUpload(@AuthenticatedEmail String email,
                                           @RequestPart("multipartFile") MultipartFile multipartFile)
            throws IOException {
        return RspTemplate.OK(imageService.imageUpload(email, multipartFile));
    }

    @PostMapping("/profile/upload")
    public RspTemplate<Void> imageProfileUpload(@AuthenticatedEmail String email,
                                                @RequestPart("multipartFile") MultipartFile multipartFile)
            throws IOException {
        imageService.memberProfileImageUpload(email, multipartFile);
        return RspTemplate.OK("프로필 이미지 업로드 성공");
    }

}
