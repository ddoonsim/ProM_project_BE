package org.choongang.configs;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.interceptors.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인터셉터만을 위한 설정 클래스
 */
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final CommonInterceptor commonInterceptor ;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor)  // 인터셉터 등록
                .addPathPatterns("/**") ;  // 모든 경로로 유입되는 요청 시
    }
}
