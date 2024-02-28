package org.choongang.todolist.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.exceptions.BadRequestException;
import org.choongang.commons.rests.JSONData;
import org.choongang.todolist.entities.Todolist;
import org.choongang.todolist.service.TodoDeleteService;
import org.choongang.todolist.service.TodoInfoService;
import org.choongang.todolist.service.TodoSaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodolistController {

    private final TodoSaveService saveService;
    private final TodoInfoService infoService;
    private final TodoDeleteService deleteService;

    @RequestMapping(method={RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity<JSONData<Object>> save(@Valid @RequestBody RequestTodo form, Errors errors) {
        errorProcess(errors);

        Todolist todo = saveService.save(form);

        JSONData<Object> data = new JSONData<>(todo);
        data.setStatus(HttpStatus.CREATED);

        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/list/{seq}")
    public JSONData<Object> getList(@PathVariable("seq") Long sSeq) {

        List<Todolist> items = infoService.getList(sSeq);

        return new JSONData<>(items);
    }

    @GetMapping("/{seq}")
    public JSONData<Object> info(@PathVariable("seq") Long seq) {
        Todolist todo = infoService.get(seq);

        return new JSONData<>(todo);
    }

    @DeleteMapping("/{seq}")
    public void delete(@PathVariable("seq") Long seq) {
        deleteService.delete(seq);
    }

    @DeleteMapping("/list/{seq}")
    public void deletes(@PathVariable("seq") Long sSeq) {
        deleteService.deleteAll(sSeq);
    }

    private void errorProcess(Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
    }
}
