package com.skhuni.skhunibackend.project.api;

import com.skhuni.skhunibackend.global.annotation.AuthenticatedEmail;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import com.skhuni.skhunibackend.image.application.ImageService;
import com.skhuni.skhunibackend.project.api.request.ProjectSaveReqDto;
import com.skhuni.skhunibackend.project.api.response.ProjectInfoResDto;
import com.skhuni.skhunibackend.project.api.response.ProjectsResDto;
import com.skhuni.skhunibackend.project.application.ProjectService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController implements ProjectControllerDocs {

    private final ProjectService projectService;
    private final ImageService imageService;

    @PostMapping("/save")
    public RspTemplate<Void> saveProject(@AuthenticatedEmail String email,
                                         @RequestBody ProjectSaveReqDto projectSaveReqDto) {
        projectService.saveProject(email, projectSaveReqDto);
        return RspTemplate.CREATED();
    }

    @PostMapping("/image/upload")
    public RspTemplate<String> projectImageUpload(@AuthenticatedEmail String email,
                                                  @RequestPart("multipartFile") MultipartFile multipartFile)
            throws IOException {
        String imageUrl = imageService.projectImageUpload(email, multipartFile);
        return RspTemplate.OK("프로젝트 이미지 업로드 성공", imageUrl);
    }

    @PostMapping("/{projectId}")
    public RspTemplate<Void> updateProject(@AuthenticatedEmail String email,
                                           @PathVariable Long projectId,
                                           @RequestBody ProjectSaveReqDto projectSaveReqDto) {
        projectService.updateProject(email, projectId, projectSaveReqDto);
        return RspTemplate.OK();
    }

    @GetMapping("/{projectId}")
    public RspTemplate<ProjectInfoResDto> getInfo(@AuthenticatedEmail String email,
                                                  @PathVariable Long projectId) {
        ProjectInfoResDto info = projectService.getInfo(email, projectId);
        return RspTemplate.OK(info);
    }

    @GetMapping("/my-projects")
    public RspTemplate<ProjectsResDto> getMyProjects(@AuthenticatedEmail String email,
                                                     @RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        ProjectsResDto myProjects = projectService.getMyProjects(email, page, size);
        return RspTemplate.OK(myProjects);
    }

    @GetMapping("/all-projects")
    public RspTemplate<ProjectsResDto> getAllProjects(@AuthenticatedEmail String email,
                                                      @RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        ProjectsResDto allProjects = projectService.getAllProjects(email, page, size);
        return RspTemplate.OK(allProjects);
    }

    @DeleteMapping("/{projectId}")
    public RspTemplate<Void> deleteProject(@AuthenticatedEmail String email,
                                           @PathVariable Long projectId) {
        projectService.deleteProject(email, projectId);
        return RspTemplate.OK();
    }
}
