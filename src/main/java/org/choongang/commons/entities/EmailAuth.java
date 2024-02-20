package org.choongang.commons.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(timeToLive = 180L)    // 필수 어노테이션, timeToLive : 유효시간 지정(초 단위)
public class EmailAuth {
    @Id
    private Long browserId;    // 회원의 브라우저 아이디(key)

    private int authCode;    // 인증 코드(value)

    private boolean verified;    // 인증 여부
}
