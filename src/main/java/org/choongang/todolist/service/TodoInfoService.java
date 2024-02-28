package org.choongang.todolist.service;

import lombok.RequiredArgsConstructor;
import org.choongang.todolist.entities.QTodolist;
import org.choongang.todolist.entities.Todolist;
import org.choongang.todolist.repositories.TodolistRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

@Service
@RequiredArgsConstructor
public class TodoInfoService {
    private final TodolistRepository todolistRepository;

    public Todolist get(Long seq) {
        Todolist todo = todolistRepository.findById(seq).orElseThrow(TodoNotFoundException::new);

        return todo;
    }

    public List<Todolist> getList(Long sSeq) {
        QTodolist todolist = QTodolist.todolist;

        List<Todolist> items = (List<Todolist>)todolistRepository.findAll(todolist.subtask.seq.eq(sSeq), Sort.by(asc("createdAt")));

        return items;
    }
}
