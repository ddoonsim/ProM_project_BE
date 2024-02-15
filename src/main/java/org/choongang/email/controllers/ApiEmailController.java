package org.choongang.email.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.rests.JSONData;
import org.choongang.email.service.EmailSendService;
import org.choongang.email.service.EmailVerifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class ApiEmailController {

    private final EmailVerifyService verifyService;
    private final EmailSendService sendService;

    /**
     * 이메일 인증 코드 발급
     */
    @GetMapping("/verify")
    public ResponseEntity<JSONData<Object>> sendVerifyEmail(@RequestParam("email") String email) {
        JSONData<Object> data = new JSONData<>();

        boolean result = verifyService.sendCode(email);
        data.setSuccess(result);

        HttpStatus status = HttpStatus.OK;
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    /**
     * 발급받은 인증코드와 사용자 입력 코드의 일치 여부 체크
     */
    @GetMapping("/auth_check")
    public JSONData<Object> checkVerifiedEmail(@RequestParam("authNum") int authNum) {
        JSONData<Object> data = new JSONData<>();

        boolean result = verifyService.check(authNum);
        data.setSuccess(result);

        return data;
    }
}
