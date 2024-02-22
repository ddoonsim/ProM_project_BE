package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.controller.FindIdValidator;
import org.choongang.member.controller.RequestFindId;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class FindIdService {

    private final FindIdValidator validator;

    public void process(RequestFindId form, Errors errors) {
        validator.validate(form, errors);
        if (errors.hasErrors()) { // 유효성 검사 실패시에는 처리 중단
            return;
        }

        // 이메일 정보 가져오기 (여기서 하는게 맞나??)
        // (form.email());

    }
}