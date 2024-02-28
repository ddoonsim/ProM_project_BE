package org.choongang.todolist.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.constants.Status;
import org.choongang.member.MemberUtil;
import org.choongang.subtask.entities.Subtask;
import org.choongang.subtask.service.SubTaskInfoService;
import org.choongang.todolist.controllers.RequestTodo;
import org.choongang.todolist.entities.Todolist;
import org.choongang.todolist.repositories.TodolistRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TodoSaveService {

    private final TodolistRepository todolistRepository;
    private final SubTaskInfoService subTaskInfoService;
    private final MemberUtil memberUtil;

    public Todolist save(RequestTodo form) {
        Long sSeq = form.sSeq(); // Subtask 등록번호
        Subtask subtask = subTaskInfoService.get(sSeq);

        Long seq = form.seq();
        Todolist todo = todolistRepository.findById(seq).orElseGet(() ->
                Todolist.builder()
                        .seq(seq)
                        .subtask(subtask)
                        .member(memberUtil.getMember())
                        .build());

        Status status = StringUtils.hasText(form.status()) ? Status.valueOf(form.status()) : Status.REQUEST;
        todo.setContent(form.content());
        todo.setStatus(status);

        todolistRepository.saveAndFlush(todo);

        return todo;
    }
}
