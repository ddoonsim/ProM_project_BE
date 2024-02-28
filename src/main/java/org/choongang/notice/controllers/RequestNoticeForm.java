package org.choongang.notice.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RequestNoticeForm(
        String gid,
        Long seq,  // 공지글 id
        Long pSeq,  // 프로젝트 seq
        @NotBlank
        String tName,  // 공지글 제목
        String description,  // 공지글 상세
        boolean isNotice
) {
}
