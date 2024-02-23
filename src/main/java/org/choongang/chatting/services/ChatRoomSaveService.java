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

    /**
     * 초기 테스트용 채팅방 생성 양식 (삭제 예정)
     * @param form
     * @return
     */
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

    /**
     * 채팅방 정보 수정시 사용
     * @param roomNm
     * @param projectSeq
     * @param roomNo
     * @return
     */
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

    /**
     * 자유 채팅방 생성
     * @param roomNm
     * @return
     */
    public ChatRoom save(String roomNm) {
        return save(roomNm, null, null);
    }

    /**
     * 프로젝트 채팅방 생성
     * @param roomNm
     * @param projectSeq
     * @return
     */
    public ChatRoom save(String roomNm, Long projectSeq) {
        return save(roomNm, projectSeq, null);
    }
}