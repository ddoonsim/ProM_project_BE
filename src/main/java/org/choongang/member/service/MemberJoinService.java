package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.constants.MemberType;
import org.choongang.member.controller.RequestJoin;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberJoinService {
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void save(RequestJoin form) {
        String password = passwordEncoder.encode(form.password());
        Member member = Member.builder()
                .name(form.name())
                .email(form.email())
                .password(password)
                .mobile(form.mobile())
                .type(MemberType.USER)
                .build();
        save(member);
    }

    public void save(Member member) {

        repository.saveAndFlush(member);
    }
}
