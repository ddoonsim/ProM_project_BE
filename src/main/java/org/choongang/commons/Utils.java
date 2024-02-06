package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component    // 스프링 관리 객체로 등록
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request ;
    private final HttpSession session ;

    /**
     * device 모드 수동 전환 혹은
     * 요청 헤더의 User-Agent 정보를 가지고 모바일인지 체크
     */
    public boolean isMobile() {
        // 모바일 수동 전환 모드 체크
        String device = (String) session.getAttribute("device") ;
        if (StringUtils.hasText(device)) {
            return device.equals("MOBILE") ;
        }

        String ua = request.getHeader("User-Agent") ;  // User-Agent 가져오기

        // 모바일 장비 체크를 위한 정규 표현식
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*" ;

        return ua.matches(pattern) ;    // 모바일이면 true
    }

    /**
     * device가 모바일인지 PC인지에 따라 view 경로 분리
     */
    public String tpl(String path) {
        String prefix = isMobile() ? "mobile/" : "front/" ;

        return prefix + path ;
    }
}
