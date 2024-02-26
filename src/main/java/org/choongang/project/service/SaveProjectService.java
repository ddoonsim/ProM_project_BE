package org.choongang.project.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.project.controllers.ProjectValidator;
import org.choongang.project.controllers.RequestProjectForm;
import org.choongang.project.entities.Project;
import org.choongang.project.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

/**
 * 프로젝트 DB 저장
 */
@Service
@RequiredArgsConstructor
public class SaveProjectService {

    private final ProjectValidator validator;
    private final ProjectRepository projectRepository;
    private final MemberUtil memberUtil;

    /**
     * 새 프로젝트 생성 & 저장 메서드
     */
    public void newProject(RequestProjectForm form, Errors errors) {

        validator.validate(form, errors);
        if (errors.hasErrors()) {
            return;  // 유효성 검사 통과 X ==> 메서드 즉시 종료
        }

        List<Member> members = new ArrayList<>();
        Member member = memberUtil.getMember();  // 현재 로그인 중인 회원 정보 가져오기
        members.add(member);

        save(form, members);  // 저장

    }

    /**
     * 프로젝트 제목 / 설명 업데이트
     */
    public void updateProjectInfo(RequestProjectForm form, Errors errors) {
        validator.validate(form, errors);
        if (errors.hasErrors()) {
            return;  // 유효성 검사 통과 X ==> 메서드 즉시 종료
        }

        List<Member> members = form.member();

        save(form, members);
    }

    /**
     * DB에 저장하는 메서드
     */
    public void save(RequestProjectForm form, List<Member> members) {
        // DB에 저장
        Project project = Project.builder()
                .seq(form.seq())
                .member(members)
                .pName(form.pname())
                .description(form.description() == null ? "" : form.description())
                .build();

        projectRepository.saveAndFlush(project);
    }
}
