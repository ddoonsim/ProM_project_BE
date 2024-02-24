package org.choongang.project.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.member.service.MemberInfoService;
import org.choongang.project.controllers.RequestProjectForm;
import org.choongang.project.entities.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateProjectService {

    private final MemberRepository memberRepository;
    private final MemberInfoService memberInfoService;

    private final ProjectInfoService projectInfoService;
    private final SaveProjectService saveProjectService;

    /**
     * 프로젝트에 팀원 추가 및 저장
     */
    public void updateMember(RequestProjectForm form, String email) {

        Member invitedMember = memberRepository.findByEmail(email).orElse(null);
        Project invitedProject = projectInfoService.viewOne(form.seq());

        // 기존 팀 멤버 리스트 가져오기
//        List<Member> teamMember = invitedProject.getMember();
//        // 초대된 계정을 리스트에 추가
//        teamMember.add(invitedMember);

        // 초대된 멤버의 참여 중인 프로젝트 리스트에 새 프로젝트 추가
        List<Project> projectList = invitedMember.getProject();
        projectList.add(invitedProject);
        invitedMember.setProject(projectList);
        // DB에 저장
        memberRepository.saveAndFlush(invitedMember);
        // DB에 저장
//        saveProjectService.save(form, teamMember);
    }
}
