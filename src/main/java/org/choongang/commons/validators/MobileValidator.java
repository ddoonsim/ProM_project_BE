package org.choongang.commons.validators;

/**
 * 휴대폰 번호 farmat 유효성 체크 인터페이스
 * - 메서드 : mobileNumCheck(String mobile)
 */
public interface MobileValidator {
    default boolean mobileNumCheck(String mobile) {
        /**
         * 010-3481-2101
         * 010_3481_2101
         * 010 3481 2101
         *
         * 1. 형식의 통일화 - 숫자가 아닌 문자 전부 제거 -> 숫자
         * 2. 패턴 생성 체크
         */
        mobile = mobile.replaceAll("\\D", "");
        String pattern = "^01[016]\\d{3,4}\\d{4}$";

        return mobile.matches(pattern);
    }
}
