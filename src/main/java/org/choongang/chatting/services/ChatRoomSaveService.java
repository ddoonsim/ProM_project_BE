package org.choongang.chatting.services;

import lombok.RequiredArgsConstructor;
import org.choongang.chatting.controllers.ChatRoomForm;
import org.choongang.chatting.entities.ChatRoom;
import org.choongang.chatting.repositories.ChatRoomRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomSaveService {
    private final ChatRoomRepository repository;

    public ChatRoom save(ChatRoomForm form) {
        Long roomNo = form.roomNo();
        ChatRoom room = null;
        if (roomNo != null) {
            room = repository.findById(roomNo).orElseThrow();
        } else {
            room = new ChatRoom();
        }

        room.setRoomNm(form.roomNm());
        room.setCapacity(form.capacity());
        repository.saveAndFlush(room);

        return room;
    }
}