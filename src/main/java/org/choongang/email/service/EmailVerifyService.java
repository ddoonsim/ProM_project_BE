package org.choongang.email.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.entities.EmailAuth;
import org.choongang.commons.repositories.EmailAuthRedisRepository;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final EmailSendService sendService;
    private final EmailAuthRedisRepository repository;
    private final MemberUtil memberUtil;

    /**
     * 이메일 인증 번호 발급 전송
     */
    public boolean sendCode(String email) {
        int authNum = (int)(Math.random() * 99999);

//        session.setAttribute("EmailAuthNum", authNum);
//        System.out.println("세션에 저장된 EmailAuthNum : " + session.getAttribute("EmailAuthNum").toString());
//        session.setAttribute("EmailAuthStart", System.currentTimeMillis());

        /* redis에 인증 정보 저장 S */
        EmailAuth emailAuth = EmailAuth.builder()
                .browserId(memberUtil.getBrowserId())
                .authCode(authNum)
                .build();

        repository.save(emailAuth);
        /* redis에 인증 정보 저장 E */

        EmailMessage emailMessage = new EmailMessage(
                email,
                Utils.getMessage("Email.verification.subject", "commons"),
                Utils.getMessage("Email.verification.message", "commons"));
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("authNum", authNum);

        return sendService.sendMail(emailMessage, "auth", tplData);
    }

    /**
     * 발급 받은 인증번호와 사용자 입력 코드와 일치 여부 체크
     */
    public boolean check(int code) {

        EmailAuth emailAuth = repository.findById(memberUtil.getBrowserId()).orElse(null);

        if (emailAuth == null || emailAuth.getAuthCode() == 0) {
            return false;
        }

        // 사용자 입력 코드와 발급 코드가 일치하는지 여부 체크
        boolean isVerified = code == emailAuth.getAuthCode();
        emailAuth.setVerified(isVerified);
        repository.save(emailAuth);

        return isVerified;
    }
}