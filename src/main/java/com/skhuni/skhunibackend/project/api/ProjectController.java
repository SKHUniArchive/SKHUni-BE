package com.skhuni.skhunibackend.project.api;

import com.skhuni.skhunibackend.global.annotation.AuthenticatedEmail;
import com.skhuni.skhunibackend.global.template.RspTemplate;
import com.skhuni.skhunibackend.project.api.request.ProjectSaveReqDto;
import com.skhuni.skhunibackend.project.api.response.ProjectInfoResDto;
import com.skhuni.skhunibackend.project.api.response.ProjectsResDto;
import com.skhuni.skhunibackend.project.application.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController implements ProjectControllerDocs {

    private final ProjectService projectService;

    @PostMapping("/save")
    public RspTemplate<Void> saveProject(@AuthenticatedEmail String email,
                                         @RequestBody ProjectSaveReqDto projectSaveReqDto) {
        projectService.saveProject(email, projectSaveReqDto);
        return RspTemplate.CREATED();
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
