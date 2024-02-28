package org.choongang.notice.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.rests.JSONData;
import org.choongang.notice.service.NoticeInfoService;
import org.choongang.notice.service.SaveNoticeService;
import org.choongang.subtask.entities.Subtask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final SaveNoticeService saveNoticeService;
    private final NoticeInfoService noticeInfoService;

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

    /**
     * 공지글 목록 가져오기
     */
    @GetMapping("/list")
    public ResponseEntity<JSONData<Object>> getList(@RequestParam("pSeq") Long pSeq) {
        List<Subtask> items = noticeInfoService.getNoticeList(pSeq);

        HttpStatus status = HttpStatus.OK;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);
        data.setData(items);

        return ResponseEntity.status(status).body(data);
    }

    @PostMapping("/update")
    public ResponseEntity<JSONData<Object>> updateNotice(@RequestBody RequestNoticeForm form) {
        saveNoticeService.update(form);

        HttpStatus status = HttpStatus.OK;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    @GetMapping("/info")
    public JSONData<Subtask> getInfo(@RequestParam("seq") Long seq) {
        Subtask subtask = noticeInfoService.getOne(seq);

        JSONData<Subtask> item = new JSONData<>(subtask);
        return item;
    }
}
