package org.choongang.chatting.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ChatRoomForm(
        Long roomNo,
        @NotBlank(message="채팅방 이름을 입력하세요.")
        String roomNm,
        int capacity) {}