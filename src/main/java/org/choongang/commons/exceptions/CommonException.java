package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public class CommonException extends RuntimeException {

    private HttpStatus status ;
    private Map<String, List<String>> messages;
    private Errors errors;

    public CommonException(Map<String, List<String>> messages, HttpStatus status) {
        super();
        this.status = status;
        this.messages = messages;
    }

    public CommonException(String message, HttpStatus status) {
        super(message);  // 메세지는 상위 클래스에 있는 것 가져오기
        this.status = status ;
    }

    public CommonException(Errors errors, HttpStatus status) {
        this.errors = errors;
        this.status = status;
    }

    public Errors getErrors() {
        return errors;
    }

    /**
     * 응답코드 status의 Getter
     */
    public HttpStatus getStatus() {
        return status ;
    }

    public Map<String, List<String>> getMessages() {
        return messages;
    }
}
