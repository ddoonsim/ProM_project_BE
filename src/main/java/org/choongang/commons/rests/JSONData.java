package org.choongang.commons.rests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * JSON 데이터 형식 고정을 위한 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JSONData<T> {
    private HttpStatus status = HttpStatus.OK ;    // 상태 코드
    private boolean success = true ;    // 성공 여부
    private T data ;    // 성공 시에 서버에 전달될 데이터
    private String message ;    // 에러 메세지
}
