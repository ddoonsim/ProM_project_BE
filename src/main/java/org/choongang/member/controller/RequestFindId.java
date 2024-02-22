package org.choongang.member.controller;

import jakarta.validation.constraints.NotBlank;

/**
 * 비밀번호 찾기 커맨드 객체 정의
 *
 */
public record RequestFindId(
        @NotBlank
        String name,

        @NotBlank
        String mobile
) {}
