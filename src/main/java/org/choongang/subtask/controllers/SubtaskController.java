package org.choongang.subtask.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.BadRequestException;
import org.choongang.commons.rests.JSONData;
import org.choongang.subtask.entities.Subtask;
import org.choongang.subtask.service.SubTaskInfoService;
import org.choongang.subtask.service.SubtaskSaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class SubtaskController {

    private final SubtaskSaveService saveService;
    private final SubTaskInfoService infoService;

    @PostMapping("/new")
    public ResponseEntity<JSONData<Object>> newTask(@RequestBody @Valid RequestSubtaskForm form, Errors errors) {
        saveService.newTask(form, errors);
        errorProcess(errors);

        HttpStatus status = HttpStatus.CREATED;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    /**
     * 업무 목록 상세
     *
     * @param seq
     * @return
     */
    @GetMapping("/sub/{seq}")
    public JSONData<Object> getSubTask(@PathVariable("seq") Long seq) {
        Subtask item = infoService.get(seq);

        return new JSONData<>(item);
    }

    /**
     * 프로젝트별 업무 목록
     *
     * @param seq 프로젝트 등록 번호
     * @return
     */
    @GetMapping("/{seq}")
    public JSONData<Object> getSubTaskList(@PathVariable("seq") Long seq) {
        List<Subtask> items = infoService.getList(seq);

        return new JSONData<>(items);
    }

    private void errorProcess(Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(Utils.getMessages(errors));
        }
    }
}
