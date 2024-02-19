package org.choongang.chatting.services;

import lombok.RequiredArgsConstructor;
import org.choongang.chatting.controllers.ChatMessageForm;
import org.choongang.chatting.entities.ChatHistory;
import org.choongang.chatting.entities.ChatRoom;
import org.choongang.chatting.repositories.ChatHistoryRepository;
import org.choongang.chatting.repositories.ChatRoomRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatRoomRepository roomRepository;
    private final ChatHistoryRepository historyRepository;

    public void save(ChatMessageForm form) {
        Long roomNo = form.roomNo();
        ChatRoom room = roomRepository.findById(roomNo).orElseThrow();

        ChatHistory history = ChatHistory.builder()
                .message(form.message())
                .chatRoom(room)
                .build();
        historyRepository.saveAndFlush(history);
    }
}
