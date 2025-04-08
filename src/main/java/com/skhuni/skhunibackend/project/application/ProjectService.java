package com.skhuni.skhunibackend.project.application;

import com.skhuni.skhunibackend.global.dto.PageInfoResDto;
import com.skhuni.skhunibackend.member.domain.Member;
import com.skhuni.skhunibackend.member.domain.repository.MemberRepository;
import com.skhuni.skhunibackend.member.exception.MemberNotFoundException;
import com.skhuni.skhunibackend.project.api.request.ProjectSaveReqDto;
import com.skhuni.skhunibackend.project.api.response.ProjectInfoResDto;
import com.skhuni.skhunibackend.project.api.response.ProjectsResDto;
import com.skhuni.skhunibackend.project.domain.Project;
import com.skhuni.skhunibackend.project.domain.repository.ProjectRepository;
import com.skhuni.skhunibackend.project.exception.ProjectAccessDeniedException;
import com.skhuni.skhunibackend.project.exception.ProjectNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveProject(String email, ProjectSaveReqDto projectSaveReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        Project project = Project.builder()
                .title(projectSaveReqDto.title())
                .picture(projectSaveReqDto.picture())
                .introLine(projectSaveReqDto.introLine())
                .introduction(projectSaveReqDto.introduction())
                .githubLink1(projectSaveReqDto.githubLink1())
                .githubLink2(projectSaveReqDto.githubLink2())
                .siteLink(projectSaveReqDto.siteLink())
                .member(member)
                .build();

        projectRepository.save(project);
    }

    @Transactional
    public void updateProject(String email, Long projectId, ProjectSaveReqDto projectSaveReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        validateProjectAccess(member, project);

        project.update(projectSaveReqDto.title(),
                projectSaveReqDto.introLine(),
                projectSaveReqDto.introduction(),
                projectSaveReqDto.githubLink1(),
                projectSaveReqDto.githubLink2(),
                projectSaveReqDto.siteLink());
    }

    public ProjectInfoResDto getInfo(String email, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        if (email != null) {
            Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

            boolean isAuthor = project.getMember().getId().equals(member.getId());
            return ProjectInfoResDto.of(project, isAuthor);
        }

        return ProjectInfoResDto.of(project);
    }

    public ProjectsResDto getMyProjects(String email, int page, int size) {
        Page<Project> projects = projectRepository.findAllByMyMemberEmail(email, page, size);

        List<ProjectInfoResDto> projectInfoResDtos = projects.getContent()
                .stream()
                .map(p -> ProjectInfoResDto.of(p, true))
                .toList();

        return ProjectsResDto.of(projectInfoResDtos, PageInfoResDto.from(projects));
    }

    public ProjectsResDto getMemberProjects(Long memberId, int page, int size) {
        Page<Project> projects = projectRepository.findAllByMemberMemberId(memberId, page, size);

        List<ProjectInfoResDto> projectInfoResDtos = projects.getContent()
                .stream()
                .map(p -> {
                    boolean isAuthor = p.getMember().getId().equals(memberId);
                    return ProjectInfoResDto.of(p, isAuthor);
                })
                .toList();

        return ProjectsResDto.of(projectInfoResDtos, PageInfoResDto.from(projects));
    }

    public ProjectsResDto getAllProjects(String email, int page, int size) {
        Page<Project> projects = projectRepository.findAll(page, size);

        List<ProjectInfoResDto> projectInfoResDtos = projects.getContent()
                .stream()
                .map(p -> {
                    if (email != null) {
                        boolean isAuthor = p.getMember().getEmail().equals(email);
                        return ProjectInfoResDto.of(p, isAuthor);
                    }

                    return ProjectInfoResDto.of(p);
                })
                .toList();

        return ProjectsResDto.of(projectInfoResDtos, PageInfoResDto.from(projects));
    }

    @Transactional
    public void deleteProject(String email, Long projectId) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        validateProjectAccess(member, project);

        projectRepository.delete(project);
    }

    private void validateProjectAccess(Member member, Project project) {
        if (!project.getMember().getId().equals(member.getId())) {
            throw new ProjectAccessDeniedException();
        }
    }

}
