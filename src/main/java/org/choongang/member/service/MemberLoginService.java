package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.configs.jwt.TokenProvider;
import org.choongang.member.controller.RequestLogin;
import org.choongang.member.controller.ResponseLogin;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLoginService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository repository;

    public ResponseLogin authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 가지고 JWT AccessToken 발급
        String accessToken = tokenProvider.createToken(authentication);

        return ResponseLogin.builder()
                .accessToken(accessToken)
                .build();
    }

    public String login(RequestLogin form) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(form.email(), form.password());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = tokenProvider.createToken(authentication); // JWT 토큰 발급

        return accessToken;
    }
}
