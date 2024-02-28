package org.choongang.notice.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.constants.BType;
import org.choongang.notice.controllers.RequestNoticeForm;
import org.choongang.project.repositories.ProjectRepository;
import org.choongang.subtask.entities.Subtask;
import org.choongang.subtask.repositories.SubtaskRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveNoticeService {

    private final SubtaskRepository subtaskRepository;
    private final ProjectRepository projectRepository;

    /**
     * 저장 메서드
     */
    public void save(RequestNoticeForm form) {

        Subtask subtask = Subtask.builder()
                .project(projectRepository.findById(form.pSeq()).orElse(null))
                .bType(BType.BOARD.name())
                .tName(form.tName())
                .description(form.description() == null ? "" : form.description())
                .isNotice(true)
                .build();
        subtaskRepository.saveAndFlush(subtask);
    }
}
