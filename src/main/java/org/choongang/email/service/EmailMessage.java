package org.choongang.email.service;

/**
 * 이메일 메세지 데이터 클래스
 */
public record EmailMessage(
        String to,    // 수신인
        String subject,    // 제목
        String message    // 내용
) {
}
