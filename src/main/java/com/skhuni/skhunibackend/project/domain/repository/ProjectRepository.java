package com.skhuni.skhunibackend.project.domain.repository;

import com.skhuni.skhunibackend.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectCustomRepository {

}
