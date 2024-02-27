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
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

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
        Subtask subtask = Subtask.builder()
                .project(projectRepository.findById(form.pSeq()).orElseThrow())
                .bType(BType.TODOLIST.name())
                .member(members)
                .tName(form.tName())
                .description(form.description() == null ? "" : form.description())
                .build();
        subtaskRepository.saveAndFlush(subtask);
    }
}
