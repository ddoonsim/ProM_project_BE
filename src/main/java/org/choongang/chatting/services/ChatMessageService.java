package org.choongang.chatting.services;

import lombok.RequiredArgsConstructor;
import org.choongang.chatting.controllers.ChatMessageForm;
import org.choongang.chatting.entities.ChatHistory;
import org.choongang.chatting.entities.ChatRoom;
import org.choongang.chatting.repositories.ChatHistoryRepository;
import org.choongang.chatting.repositories.ChatRoomRepository;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatRoomRepository roomRepository;
    private final ChatHistoryRepository historyRepository;
    private final MemberRepository memberRepository;
    public void save(ChatMessageForm form) {
        System.out.println(form);
        Long roomNo = form.roomNo();
        Long memberSeq = Long.parseLong(form.memberSeq());
        ChatRoom room = roomRepository.findById(roomNo).orElseThrow();

        ChatHistory history = ChatHistory.builder()
                .member(memberRepository.findById(memberSeq).orElse(null))
                .message(form.message())
                .chatRoom(room)
                .build();
        historyRepository.saveAndFlush(history);
    }
}
