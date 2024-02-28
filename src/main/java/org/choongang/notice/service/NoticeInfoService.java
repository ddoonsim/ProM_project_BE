package org.choongang.notice.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.choongang.subtask.entities.QSubtask;
import org.choongang.subtask.entities.Subtask;
import org.choongang.subtask.repositories.SubtaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeInfoService {

    private final SubtaskRepository repository;
    private final EntityManager em;

    /**
     * 프로젝트에 등록된 공지 리스트 가져오기
     */
    public List<Subtask> getNoticeList(Long pSeq) {
        QSubtask subtask = QSubtask.subtask;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(subtask.isNotice.isTrue());
        andBuilder.and(subtask.project.seq.eq(pSeq));

        PathBuilder<Subtask> pathBuilder = new PathBuilder<>(Subtask.class, "subtask");
        List<Subtask> lists = new JPAQueryFactory(em)
                .selectFrom(subtask)
                .leftJoin(subtask.project)
                .fetchJoin()
                .where(andBuilder)
                .orderBy(new OrderSpecifier(Order.DESC, pathBuilder.get("createdAt")))    // 최신게시글 순서로 정렬
                .fetch();

        return lists;
    }

    public Subtask getOne(Long seq) {
        Subtask subtask = repository.findById(seq).orElse(null);
        return subtask;
    }
}
