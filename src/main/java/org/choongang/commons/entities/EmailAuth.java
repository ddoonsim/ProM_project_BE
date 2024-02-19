package org.choongang.commons.entities;

import jakarta.validation.constraints.NotBlank;
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
@RedisHash( timeToLive = 60 * 3L ) // 자동 파기 시간 (초단위)
public class EmailAuth {
    @Id
    private Long browserId;

    private int authCode;

    private boolean verified;
}
