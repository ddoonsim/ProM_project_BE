package org.choongang.myPage.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.BadRequestException;
import org.choongang.commons.rests.JSONData;
import org.choongang.member.service.MemberUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MyPageController implements ExceptionProcessor {

    private final ProfileValidator profileValidator;

    private final MemberUpdateService memberUpdateService;

    @PostMapping("/edit")
    public ResponseEntity<JSONData<Object>> updateProfile(@RequestBody @Valid RequestProfile form, Errors errors) {
        profileValidator.validate(form, errors);
        errorProcess(errors);
        memberUpdateService.update(form);

        HttpStatus status = HttpStatus.CREATED;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    private void errorProcess(Errors errors) {
        if(errors.hasErrors()) {
            throw new BadRequestException(Utils.getMessages(errors));
        }
    }
}