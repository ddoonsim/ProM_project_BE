package org.choongang.configs;

import org.choongang.configs.jwt.CustomJwtFilter;
import org.choongang.configs.jwt.JwtAccessDeniedHandler;
import org.choongang.configs.jwt.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * 시큐리티 설정 클래스
 */
@Configuration
@EnableWebSecurity // 기본 웹 보안 활성화
@EnableMethodSecurity // @PreAuthorize 애노테이션 활성화
public class SecurityConfig {

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private CustomJwtFilter customJwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(c -> {
                    c.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler);
                })
                .authorizeHttpRequests(c -> {
                    c.requestMatchers("/api/v1/member",
                                    "/api/v1/member/token",
                                    "/api/v1/member/login",
                                    "/api/v1/member/info",
                                    "/api/v1/member/email_dup_check",
                                    "/api/v1/member/find_pw",
                                    "/api/v1/member/find_id",
                                    "/api/v1/email/**",
                                    "/api/v1/email/auth_check",
                                    "/api/v1/member/exists/**",
                                    "/api/v1/file/**",
                                    "/uploads/**",
                                    "/api/v1/chat/", "/chat" // 개발 완료 후 삭제!
                            ).permitAll()
                            .anyRequest().authenticated();
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}