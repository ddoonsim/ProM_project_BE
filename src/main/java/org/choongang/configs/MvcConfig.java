package org.choongang.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(FileProperties.class)  // 분리된 파일 업로드를 위한 설정 클래스를 포함시킴
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private FileProperties fileProperties ;

    @Override
    // 정적 자원 경로 설정
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileProperties.getUrl() + "**")  // ** : 하위 경로 모두 포함
                .addResourceLocations("file:///" + fileProperties.getPath()) ;
    }

    @Bean
    // 메세지 파일(.properties) 설정
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource() ;
        ms.setDefaultEncoding("UTF-8");    // encoding 언어 설정
        ms.setBasenames("messages.commons", "messages.validations", "messages.errors");  // 메세지 파일 등록

        return ms ;
    }

    /**
     * form 태그 내에서 일부 데이터의 서버 전송 방식(method)를 POST, GET 이외의 방식으로 설정하기 위한 사전 설정 작업
     * ==> type="hidden" name="_method" value="PATCH/DELETE 등"
     */
    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        return new HiddenHttpMethodFilter() ;
    }
}
