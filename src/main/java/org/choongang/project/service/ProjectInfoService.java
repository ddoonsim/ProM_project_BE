package org.choongang.project.service;

import lombok.RequiredArgsConstructor;
import org.choongang.project.entities.Project;
import org.choongang.project.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectInfoService {

    private final ProjectRepository projectRepository;

    /**
     * 회원 별 프로젝트 목록 가져오기
     */
    public List<Project> getProjects(Long memberSeq) {
        List<Project> projects = projectRepository.findByMemberSeq(memberSeq).orElse(null);
        return projects;
    }

    /**
     * 프로젝트 조회
     */
    public Project viewOne(Long projectSeq) {
        Project project = projectRepository.findById(projectSeq).orElse(null);
        return project;
    }
}
