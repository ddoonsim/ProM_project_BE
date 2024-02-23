package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.member.controller.FindIdValidator;
import org.choongang.member.controller.RequestFindId;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class FindIdService {

    private final FindIdValidator validator;
    private final MemberRepository repository;
    public String process(RequestFindId form, Errors errors) {
        validator.validate(form, errors);
        if (errors.hasErrors()) { // 유효성 검사 실패시에는 처리 중단
            return null;
        }

        // 이메일 정보 가져오기 (여기서 하는게 맞나??)
        // (form.email());
        BooleanBuilder builder = new BooleanBuilder();
        QMember member = QMember.member;
        builder.and(member.name.eq(form.name())).and(member.mobile.eq(form.mobile().replaceAll("\\D", "")));
        Member _member = repository.findOne(builder).orElse(null);

        return _member.getEmail();
    }
}