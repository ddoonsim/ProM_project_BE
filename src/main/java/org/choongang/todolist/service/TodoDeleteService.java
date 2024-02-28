package org.choongang.todolist.service;

import lombok.RequiredArgsConstructor;
import org.choongang.todolist.entities.Todolist;
import org.choongang.todolist.repositories.TodolistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoDeleteService {

    private final TodolistRepository todolistRepository;
    private final TodoInfoService todoInfoService;

    public void delete(Long seq) {
        todolistRepository.deleteById(seq);
        todolistRepository.flush();
    }

    public void deleteAll(Long sSeq) {
        List<Todolist> items = todoInfoService.getList(sSeq);
        todolistRepository.deleteAll(items);
        todolistRepository.flush();
    }
}
