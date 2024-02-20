package org.choongang.chatting.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.chatting.entities.ChatRoom;
import org.choongang.chatting.services.ChatMessageService;
import org.choongang.chatting.services.ChatRoomInfoService;
import org.choongang.chatting.services.ChatRoomSaveService;
import org.choongang.commons.exceptions.BadRequestException;
import org.choongang.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatRoomSaveService chatRoomSaveService;
    private final ChatRoomInfoService chatRoomInfoService;
    private final ChatMessageService messageService;

    @GetMapping("/rooms")
    public ResponseEntity<JSONData<List<ChatRoom>>> rooms() {
        List<ChatRoom> rooms = chatRoomInfoService.getList();
        JSONData<List<ChatRoom>> data = new JSONData<>();
        data.setSuccess(true);
        data.setData(rooms);

        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/room/{roomNo}")
    public JSONData<ChatRoom> roomInfo(@PathVariable("roomNo") Long roomNo) {
        System.out.println("=================/api/v1/chat/room/"+roomNo);
        ChatRoom room = chatRoomInfoService.get(roomNo);
        JSONData<ChatRoom> data = new JSONData<>();
        data.setData(room);

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
}
