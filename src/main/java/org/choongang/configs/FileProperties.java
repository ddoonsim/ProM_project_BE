package org.choongang.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 파일 업로드를 위한 설정 클래스 --> MvcConfig 클래스에서 파일 정적 경로로 설정
 */
@Data
@ConfigurationProperties(prefix = "file.upload")    // path와 url 앞에 붙는 prefix 설정
public class FileProperties {
    private String path ;    // 파일 경로
    private String url ;     // url 주소
}
