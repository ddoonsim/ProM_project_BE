package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {

    private HttpStatus status ;

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
}
