package org.choongang.chatting.services;

import lombok.RequiredArgsConstructor;
import org.choongang.chatting.controllers.ChatRoomForm;
import org.choongang.chatting.entities.ChatRoom;
import org.choongang.chatting.repositories.ChatRoomRepository;
import org.choongang.project.entities.Project;
import org.choongang.project.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomSaveService {
    private final ChatRoomRepository repository;
    private final ProjectRepository projectRepository;

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

    public ChatRoom save(String roomNm, Long projectSeq, Long roomNo) {
        ChatRoom room = null;

        if (roomNo != null) {
            room = repository.findById(roomNo).orElseThrow();
        } else {
            room = new ChatRoom();
        }

        Project p = projectRepository.findById(projectSeq).orElseThrow();

        room.setRoomNm(roomNm);
        room.setProject(p);
        room.setCapacity(20);   // 추후 프로젝트 최대 인원수로 변경
        repository.saveAndFlush(room);

        return room;
    }

    public ChatRoom save(String roomNm) {
        return save(roomNm, null, null);
    }
    public ChatRoom save(String roomNm, Long projectSeq) {
        return save(roomNm, projectSeq, null);
    }
}