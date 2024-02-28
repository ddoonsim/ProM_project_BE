package org.choongang.notice.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.constants.BType;
import org.choongang.file.service.FileUploadService;
import org.choongang.notice.controllers.RequestNoticeForm;
import org.choongang.project.repositories.ProjectRepository;
import org.choongang.subtask.entities.Subtask;
import org.choongang.subtask.repositories.SubtaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveNoticeService {

    private final SubtaskRepository subtaskRepository;
    private final ProjectRepository projectRepository;
    private final FileUploadService fileUploadService;

    /**
     * 저장 메서드
     */
    public void save(RequestNoticeForm form) {

        String gid = StringUtils.hasText(form.gid()) ? form.gid() : UUID.randomUUID().toString();

        Subtask subtask = Subtask.builder()
                .gid(gid)
                .project(projectRepository.findById(form.pSeq()).orElseThrow())
                .bType(BType.BOARD.name())
                .tName(form.tName())
                .description(form.description() == null ? "" : form.description())
                .isNotice(true)
                .build();
        subtaskRepository.saveAndFlush(subtask);

        fileUploadService.processDone(subtask.getGid());
    }
}
