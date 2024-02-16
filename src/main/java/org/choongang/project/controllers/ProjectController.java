package org.choongang.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.BadRequestException;
import org.choongang.commons.rests.JSONData;
import org.choongang.project.service.SaveProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final SaveProjectService saveService;

    /**
     * 새 프로젝트 생성
     */
    @GetMapping("/new")
    public ResponseEntity<JSONData<Object>> newProject(@RequestBody @Valid RequestProjectForm form, Errors errors) {

        saveService.newProject(form, errors);
        errorProcess(errors);

        HttpStatus status = HttpStatus.CREATED;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    private void errorProcess(Errors errors) {
        if (errors.hasErrors()) {
            List<String> errorMessages = Utils.getMessages(errors);
            throw new BadRequestException(errorMessages.stream().collect(Collectors.joining("||")));
        }
    }

    /**
     * 프로젝트 설정 수정
     */
    @GetMapping("/edit")
    public void editProject() {}

    /**
     * 프로젝트에 참여자 초대
     */
    @GetMapping("/invite")
    public void inviteMember() {}

    /**
     * 프로젝트 삭제
     */
    @GetMapping("delete")
    public void deleteProject() {}
}
