package org.choongang.subtask.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.constants.BType;
import org.choongang.commons.constants.Status;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.project.entities.Project;
import org.choongang.project.repositories.ProjectRepository;
import org.choongang.subtask.controllers.RequestSubtaskForm;
import org.choongang.subtask.controllers.SubtaskValidator;
import org.choongang.subtask.entities.Subtask;
import org.choongang.subtask.repositories.SubtaskRepository;
import org.choongang.todolist.controllers.RequestTodo;
import org.choongang.todolist.entities.Todolist;
import org.choongang.todolist.repositories.TodolistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubtaskSaveService {

    private final SubtaskValidator validator;
    private final SubtaskRepository subtaskRepository;
    private final ProjectRepository projectRepository;
    private final TodolistRepository todolistRepository;
    private final FileUploadService fileUploadService;
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;

    /**
     * 업무 등록, 수정
     *
     * @param form
     * @param errors
     */
    public Subtask saveTask(RequestSubtaskForm form, Errors errors) {
        System.out.println("form======" + form);
        validator.validate(form, errors);
        if(errors.hasErrors()) {
            return null;
        }

        QMember member = QMember.member;
        Long seq = form.seq();
        Subtask subtask = null;
        if (seq == null) {
            Project project = projectRepository.findById(form.pSeq()).orElse(null);
            subtask =  Subtask.builder()
                    .project(project)
                    .gid(form.gid())
                    .build();
        } else {
            subtask = subtaskRepository.findById(seq).orElseThrow(SubtaskNotFoundException::new);

        };
        /*
        List<Long> memberSeq = form.members();
        List<Member> members = null;
        if (memberSeq != null && !memberSeq.isEmpty()) {
            members = (List<Member>) memberRepository.findAll(member.seq.in(memberSeq));
        }

        subtask.setMember(members);
        */
        List<Long> memberSeq = form.members();
        if (memberSeq != null && !memberSeq.isEmpty()) {
            String memberSeqs = memberSeq.stream().map(String::valueOf).collect(Collectors.joining(","));
            subtask.setMemberSeqs(memberSeqs);
        }
        System.out.println(form.memberSeqs());
        Status status = StringUtils.hasText(form.status()) ? Status.valueOf(form.status()) : Status.REQUEST;

        subtask.setTName(form.tname());
        subtask.setDescription(form.description());
        subtask.setStatus(status);
        subtask.setSDate(form.sdate());
        subtask.setEDate(form.edate());
        subtask.setMember(memberUtil.getMember());


        subtaskRepository.saveAndFlush(subtask);

        List<Todolist> todos = todolistRepository.findBySubtask(subtask);
        if (todos != null && !todos.isEmpty()) {
            todolistRepository.deleteAll(todos);
            todolistRepository.flush();
        }

        List<RequestTodo> rTodos = form.todos();
        if (rTodos != null && !rTodos.isEmpty()) {
            List<Todolist> items = new ArrayList<>();

            for (RequestTodo todo : rTodos) {
                Todolist item = Todolist.builder()
                        .subtask(subtask)
                        .content(todo.content())
                        .done(todo.done())
                        .build();
                items.add(item);
            }

            todolistRepository.saveAllAndFlush(items);
        }

        Subtask newSubTask = subtaskRepository.findById(subtask.getSeq()).orElse(null);

        fileUploadService.processDone(subtask.getGid());

        return newSubTask;
    }

    public void newTask(RequestSubtaskForm form, Errors errors) {
        validator.validate(form, errors);
        if(errors.hasErrors()) {
            return;
        }
        /*
        List<Long> memberSeq = form.member();
        List<Member> members = new ArrayList<>();

        memberSeq.forEach(i -> members.add(memberRepository.findById(i).orElseThrow()));
        save(form, members);

         */
    }

    public void save(RequestSubtaskForm form, List<Member> members){
        /*
        String gid = form.gid();
        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();
        Status status = StringUtils.hasText(form.status()) ? Status.valueOf(form.status()) : Status.REQUEST;

        Subtask subtask = Subtask.builder()
                .project(projectRepository.findById(form.pSeq()).orElseThrow())
                .bType(BType.TODOLIST.name())
                .gid(gid)
                .memberSeqs()
                .tName(form.tname())
                .status(status)
                .sDate(form.sdate())
                .eDate(form.edate())
                .description(form.description() == null ? "" : form.description())
                .build();

        subtaskRepository.saveAndFlush(subtask);

         */
    }
}
