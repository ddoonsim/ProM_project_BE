package org.choongang.subtask.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.constants.BType;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.project.repositories.ProjectRepository;
import org.choongang.subtask.controllers.RequestSubtaskForm;
import org.choongang.subtask.controllers.SubtaskValidator;
import org.choongang.subtask.entities.Subtask;
import org.choongang.subtask.repositories.SubtaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubtaskSaveService {

    private final SubtaskValidator validator;
    private final MemberRepository memberRepository;
    private final SubtaskRepository subtaskRepository;
    private final ProjectRepository projectRepository;

    public void newTask(RequestSubtaskForm form, Errors errors) {
        validator.validate(form, errors);
        if(errors.hasErrors()) {
            return;
        }
        System.out.println(form);
        List<Long> memberSeq = form.member();
        List<Member> members = new ArrayList<>();

        memberSeq.forEach(i -> members.add(memberRepository.findById(i).orElseThrow()));
        save(form, members);
    }

    public void save(RequestSubtaskForm form, List<Member> members){

        String gid = form.gid();
        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();

        Subtask subtask = Subtask.builder()
                .project(projectRepository.findById(form.pSeq()).orElseThrow())
                .bType(BType.TODOLIST.name())
                .gid(gid)
                .member(members)
                .tName(form.tName())
                .description(form.description() == null ? "" : form.description())
                .build();
        subtaskRepository.saveAndFlush(subtask);
    }
}
