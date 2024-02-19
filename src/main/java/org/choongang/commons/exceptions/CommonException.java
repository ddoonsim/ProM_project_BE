package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class CommonException extends RuntimeException {

    private HttpStatus status ;
    private Map<String, List<String>> messages;

    public CommonException(Map<String, List<String>> messages, HttpStatus status) {
        super();
        this.status = status;
        this.messages = messages;
    }

    public CommonException(String message, HttpStatus status) {
        super(message);  // 메세지는 상위 클래스에 있는 것 가져오기
        this.status = status ;
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
