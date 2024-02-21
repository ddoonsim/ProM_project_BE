package org.choongang.chatting.services;

import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class RoomNotFoundException extends CommonException {
    public RoomNotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);

    }
}