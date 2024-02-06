package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;

/**
 * 에러코드 400일 때의 에러 처리 클래스
 */
public class BadRequestException extends CommonException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
