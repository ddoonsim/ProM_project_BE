package org.choongang.member.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.validators.MobileValidator;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FindIdValidator implements Validator, MobileValidator {

    private final MemberRepository memberRepository;


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestFindId.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RequestFindId form = (RequestFindId) target;
        String name = form.name();
        String mobile = form.mobile().replaceAll("\\D", "");

        // 1. 휴대전화번호 + 회원명 조합으로 조회 되는지 체크
        if (StringUtils.hasText(mobile) && !memberRepository.existsByMobileAndName(name, mobile).orElse(false)) {
            errors.rejectValue("name", "NotFound");
        }

        // 2. 휴대전화번호 형식 체크
        if (mobile != null && !mobile.isBlank() && !mobileNumCheck(mobile)) {
            errors.rejectValue("mobile", "Mobile");
        }
    }
}
