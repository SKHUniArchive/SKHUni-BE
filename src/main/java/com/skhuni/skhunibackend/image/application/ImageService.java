package com.skhuni.skhunibackend.image.application;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.skhuni.skhunibackend.image.domain.Image;
import com.skhuni.skhunibackend.image.domain.repository.ImageRepository;
import com.skhuni.skhunibackend.member.domain.Member;
import com.skhuni.skhunibackend.member.domain.repository.MemberRepository;
import com.skhuni.skhunibackend.member.exception.MemberNotFoundException;
import com.skhuni.skhunibackend.project.domain.Project;
import com.skhuni.skhunibackend.project.domain.repository.ProjectRepository;
import com.skhuni.skhunibackend.project.exception.ProjectNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public String imageUpload(String email, MultipartFile multipartFile) throws IOException {
        String uuid = getUuid();
        Storage storage = getStorage();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        String filePath = "member/images/" + member.getId() + "/" + uuid;
        String imgUrl = getImgUrl(filePath);

        storageSave(multipartFile, filePath, storage);
        Image image = Image.builder()
                .convertImageUrl(imgUrl)
                .member(member)
                .build();
        imageRepository.save(image);

        return image.getConvertImageUrl();
    }

    @Transactional
    public void memberProfileImageUpload(String email, MultipartFile multipartFile) throws IOException {
        String uuid = getUuid();
        Storage storage = getStorage();

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        String filePath = "member/profile/" + member.getId() + "/" + uuid;
        String imgUrl = getImgUrl(filePath);

        if (multipartFile != null) {
            storageSave(multipartFile, filePath, storage);
            Image image = Image.builder()
                    .convertImageUrl(imgUrl)
                    .member(member)
                    .build();

            imageRepository.save(image);
            member.updatePicture(image.getConvertImageUrl());
        }
    }

    @Transactional
    public void projectImageUpload(String email, Long projectId, MultipartFile multipartFile) throws IOException {
        String uuid = getUuid();
        Storage storage = getStorage();

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        String filePath = "project/" + member.getId() + "/" + projectId + "/" + uuid;
        String imgUrl = getImgUrl(filePath);

        if (multipartFile != null) {
            Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
            storageSave(multipartFile, filePath, storage);
            Image image = Image.builder()
                    .convertImageUrl(imgUrl)
                    .member(member)
                    .project(project)
                    .build();

            imageRepository.save(image);
            project.updatePicture(image.getConvertImageUrl());
        }
    }

    private static String getUuid() {
        return UUID.randomUUID().toString();
    }

    private Storage getStorage() throws IOException {
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();

        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();
    }

    private String getImgUrl(String filePath) {
        return "https://storage.googleapis.com/" + bucketName + "/" + filePath;
    }

    private void storageSave(MultipartFile file, String filePath, Storage storage) throws IOException {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, filePath)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getInputStream());
    }

}
