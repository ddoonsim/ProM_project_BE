package org.choongang.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 공통 인터셉터
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        checkDevice(request);    // 함수로 기능 분리

        return true ;
    }

    /**
     * device의 PC or mobile 수동 변경 처리
     */
    private void checkDevice(HttpServletRequest request) {
        // device : PC ==> PC 뷰 | mobile ==> mobile 뷰
        String device = request.getParameter("device") ;
        if (!StringUtils.hasText(device)) {    // device 파라미터 값이 없을 때
            return;
        }
        // device 파라미터 값이 있을 때
        device = device.toUpperCase().equals("MOBILE") ? "MOBILE" : "PC" ;

        HttpSession session = request.getSession();
        session.setAttribute("device", device);
    }
}
