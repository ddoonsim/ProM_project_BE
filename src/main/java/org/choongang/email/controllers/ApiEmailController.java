package org.choongang.email.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.rests.JSONData;
import org.choongang.email.service.EmailMessage;
import org.choongang.email.service.EmailSendService;
import org.choongang.email.service.EmailVerifyService;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.project.entities.Project;
import org.choongang.project.service.ProjectInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class ApiEmailController {

    private final EmailVerifyService verifyService;
    private final EmailSendService sendService;
    private final MemberRepository memberRepository;
    private final ProjectInfoService projectInfoService;

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
    public ResponseEntity<JSONData<Object>> sendInvitation(@RequestParam("email") String email,
                                                           @RequestParam("link") String invitationUrl) {
        JSONData<Object> data = new JSONData<>();
        HttpStatus status = HttpStatus.OK;

        /* 프로젝트에 이미 가입된 이메일 계정이 있는지 확인 S */
        Long projectSeq = Long.parseLong(invitationUrl.substring(invitationUrl.lastIndexOf("/") + 1));
        Project project = projectInfoService.viewOne(projectSeq);
        boolean isExist = project.getMember().contains(memberRepository.findByEmail(email).orElse(null));
        if (isExist) {
            status = HttpStatus.BAD_REQUEST;
            data.setSuccess(false);
            data.setStatus(status);

            return ResponseEntity.status(status).body(data);
        }
        /* 프로젝트에 이미 가입된 이메일 계정이 있는지 확인 E */

        EmailMessage emailMessage = new EmailMessage(
                email,
                Utils.getMessage("Email.invitation.subject", "commons"),
                Utils.getMessage("Email.invitation.message", "commons"));
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("invitationUrl", invitationUrl);

        boolean result = sendService.sendMail(emailMessage, "invitation", tplData);

        data.setSuccess(result);

        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }
}
