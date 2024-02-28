package org.choongang.notice.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.rests.JSONData;
import org.choongang.notice.service.SaveNoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final SaveNoticeService saveNoticeService;

    /**
     * 새 공지글 등록
     */
    @PostMapping("/new")
    public ResponseEntity<JSONData<Object>> newNotice(@RequestBody RequestNoticeForm form) {

        saveNoticeService.save(form);

        HttpStatus status = HttpStatus.CREATED;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }
}
