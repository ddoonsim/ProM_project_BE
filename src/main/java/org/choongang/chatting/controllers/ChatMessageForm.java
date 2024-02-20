package org.choongang.chatting.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ChatMessageForm(

        @NotBlank(message="로그인 하십시오.")
        String memberSeq,

        @NotBlank(message="메세지를 입력하세요.")
        String message,
        @NotNull(message="채팅방을 선택하세요.")
        Long roomNo) {}