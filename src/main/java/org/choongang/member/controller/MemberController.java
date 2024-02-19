package org.choongang.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.BadRequestException;
import org.choongang.commons.rests.JSONData;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.member.service.MemberInfo;
import org.choongang.member.service.MemberJoinService;
import org.choongang.member.service.MemberLoginService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberJoinService joinService ;
    private final MemberLoginService loginService ;
    private final MemberRepository memberRepository;
    private final MemberUtil memberUtil;

    /**
     * accessToken 발급
     *
     */
    @PostMapping("/token")
    public ResponseEntity<JSONData> token(@RequestBody @Valid RequestLogin form, Errors errors) {
        errorProcess(errors);

        String accessToken = loginService.login(form);

        /**
         * 1. 응답 body - JSONData 형식으로
         * 2. 응답 헤더 - Authorization: Bearer 토큰
         */

        JSONData data = new JSONData(accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        return ResponseEntity.status(data.getStatus()).headers(headers).body(data);
    }

    /**
     * 회원가입 처리
     */
    @PostMapping
    public ResponseEntity<JSONData<Object>> join(@RequestBody @Valid RequestJoin form, Errors errors) {
        joinService.save(form, errors);

        // 유효성 검사 처리
        errorProcess(errors);

        HttpStatus status = HttpStatus.CREATED;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    /**
     * 이메일 중복 여부 체크
     */
    @GetMapping("/email_dup_check")
    public ResponseEntity<JSONData<Object>> duplicateEmailCheck(@RequestParam("email") String email) {
        boolean isExists = memberRepository.exists(email);
        HttpStatus status = HttpStatus.OK;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false);

        if (isExists) {
            data.setSuccess(isExists);
            data.setStatus(status);

            return ResponseEntity.status(status).body(data);
        }

        return ResponseEntity.status(status).body(data);
    }

    private void errorProcess(Errors errors) {
        if (errors.hasErrors()) {
            List<String> errorMessages = Utils.getMessages(errors);
            throw new BadRequestException(errorMessages.stream().collect(Collectors.joining("||")));
        }
    }

    @GetMapping("/info")
    public JSONData info(@AuthenticationPrincipal MemberInfo memberInfo) {
        Member member = memberInfo.getMember();

        return new JSONData(member);
    }


    @GetMapping("/member_only")
    public void MemberOnlyUrl() {
        log.info("회원 전용 URL 접근 테스트");
    }

    @GetMapping("/admin_only")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void adminOnlyUrl() {
        log.info("관리자 전용 URL 접근 테스트");
    }
}