package org.choongang.chatting.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.chatting.entities.ChatHistory;
import org.choongang.chatting.entities.ChatRoom;
import org.choongang.chatting.services.ChatMessageInfoService;
import org.choongang.chatting.services.ChatMessageService;
import org.choongang.chatting.services.ChatRoomInfoService;
import org.choongang.chatting.services.ChatRoomSaveService;
import org.choongang.commons.exceptions.BadRequestException;
import org.choongang.commons.rests.JSONData;
import org.choongang.project.entities.Project;
import org.choongang.project.service.ProjectInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatRoomSaveService chatRoomSaveService;
    private final ChatRoomInfoService chatRoomInfoService;
    private final ChatMessageService messageService;
    private final ChatMessageInfoService messageInfoService;
    private final ProjectInfoService projectInfoService;

    @GetMapping("/rooms")
    public ResponseEntity<JSONData<List<ChatRoom>>> rooms() {
        List<ChatRoom> rooms = chatRoomInfoService.getList();
        JSONData<List<ChatRoom>> data = new JSONData<>();
        data.setSuccess(true);
        data.setData(rooms);

        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/room/{roomNo}")
    public JSONData<List<ChatHistory>> roomInfo(@PathVariable("roomNo") String sNo) {
        System.out.println("=================/api/v1/chat/room/"+sNo);

        Long lNo = projectProvider(sNo);

        ChatRoom room = chatRoomInfoService.get(lNo);
        ChatHistory defaultHistory = new ChatHistory(null, room, null, null);
        List<ChatHistory> messages = messageInfoService.getList(lNo);
        messages.add(0,defaultHistory);
        System.out.println(messages);

        JSONData<List<ChatHistory>> data = new JSONData<>();
        data.setData(messages);


        return data;
    }

    @PostMapping("/room")
    public ResponseEntity<JSONData<ChatRoom>> registerRoom(@Valid @RequestBody ChatRoomForm form, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getAllErrors()
                    .stream().map(ObjectError::getDefaultMessage).findFirst().orElse(null);

            throw new BadRequestException(message);
        }

        ChatRoom room = chatRoomSaveService.save(form);
        HttpStatus status = HttpStatus.CREATED;

        JSONData<ChatRoom> data = new JSONData<>();
        data.setSuccess(true);
        data.setData(room);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    @PostMapping("/message")
    public ResponseEntity<JSONData<Object>> registerMessage(@Valid @RequestBody ChatMessageForm form, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getAllErrors()
                    .stream().map(ObjectError::getDefaultMessage).findFirst().orElse(null);

            throw new BadRequestException(message);
        }

        messageService.save(form);
        HttpStatus status = HttpStatus.CREATED;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    /**
     * 요청 채팅방 번호에 p가 포함되어있으면 projectSeq!
     * projectSeq로 chatRoom을 검색해서
     * chatRoom의 roomNo를 반환!
     *
     * @param seq
     * @return
     */
    private Long projectProvider (String seq) {
        Long lNo = 0L;

        if (seq.contains("p")){
            Long pNo = Long.parseLong(seq.replace("p",""));

            Project p = projectInfoService.viewOne(pNo);

            ChatRoom room = chatRoomInfoService.getByProjectSeq(p.getSeq());

            lNo = room.getRoomNo();

        } else {
            lNo = Long.parseLong(seq);
        }
        return lNo;
    }
}
