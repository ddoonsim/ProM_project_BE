package org.choongang.email.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.rests.JSONData;
import org.choongang.email.service.EmailMessage;
import org.choongang.email.service.EmailSendService;
import org.choongang.email.service.EmailVerifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<JSONData<Object>> checkVerifiedEmail(@RequestParam("authNum") int authNum) {
        JSONData<Object> data = new JSONData<>();
        HttpStatus status = HttpStatus.OK;

        boolean result = verifyService.check(authNum);
        data.setSuccess(result);

        return ResponseEntity.status(status).body(data);
    }

    /**
     * 프로젝트 초대 이메일 발송
     */
    @GetMapping("/invite")
    public ResponseEntity<JSONData<Object>> sendInvitation(@RequestParam("email") String email, @RequestParam("link") String invitationUrl) {

        EmailMessage emailMessage = new EmailMessage(
                email,
                Utils.getMessage("Email.invitation.subject", "commons"),
                Utils.getMessage("Email.invitation.message", "commons"));
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("invitationUrl", invitationUrl);

        boolean result = sendService.sendMail(emailMessage, "invitation", tplData);

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(result);

        HttpStatus status = HttpStatus.OK;
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }
}
