package org.choongang.member.controller;

import lombok.Builder;

@Builder
public record ResponseLogin(
        String accessToken
) {}