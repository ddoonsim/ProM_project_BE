package org.choongang.notice.service;

import lombok.RequiredArgsConstructor;
import org.choongang.file.service.FileDeleteService;
import org.choongang.notice.controllers.RequestNoticeForm;
import org.choongang.subtask.repositories.SubtaskRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteNoticeService {

    private final SubtaskRepository repository;
    private final FileDeleteService fileDeleteService;

    public void deleteNotice(RequestNoticeForm form) {
        Long seq = form.seq();

        repository.deleteById(seq);
        repository.flush();

        fileDeleteService.delete(form.gid());
    }
}
