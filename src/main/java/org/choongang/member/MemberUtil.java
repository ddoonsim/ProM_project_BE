package org.choongang.member;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.configs.jwt.CustomJwtFilter;
import org.choongang.member.entities.Member;
import org.choongang.member.service.MemberInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final CustomJwtFilter customJwtFilter;
    private final HttpServletRequest request;

    /**
     * 현재 로그인되어 있는 회원 객체 가져오기
     */
    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof MemberInfo) {
            MemberInfo memberInfo = (MemberInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return memberInfo.getMember();
        }

        return null;
    }

    /**
     * 로그인 중인지
     */
    public boolean isLogin() {
        return getMember() != null;
    }

    /**
     * 토큰 가져오기
     */
    public String getAuthToken() {
        return customJwtFilter.resolveToken(request);
    }

    /**
     * 브라우저 아이디 --> 식별 가능한 키
     */
    public long getBrowserId() {
        String value = request.getHeader("browserId");
        return StringUtils.hasText(value) ? Long.parseLong(value) : 0L;
    }
}
