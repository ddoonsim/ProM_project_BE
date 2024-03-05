package org.choongang.subtask.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.project.entities.Project;
import org.choongang.project.service.ProjectInfoService;
import org.choongang.subtask.entities.QSubtask;
import org.choongang.subtask.entities.Subtask;
import org.choongang.subtask.repositories.SubtaskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Order.asc;

@Service
@RequiredArgsConstructor
@Transactional
public class SubtaskInfoService {
    private final SubtaskRepository subtaskRepository;
    private final ProjectInfoService projectInfoService;
    private final EntityManager em;
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;

    public List<Subtask> getList(Long seq) {
        Project project = projectInfoService.viewOne(seq);

        QSubtask subtask = QSubtask.subtask;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(subtask.project.eq(project));

        List<Subtask> items = (List<Subtask>)subtaskRepository.findAll(andBuilder, Sort.by(asc("createdAt")));
        items.forEach(this::addInfo);


        return items;
    }

    /**
     * 현재 로그인한 회원이 담당자로 설정되어 있는 tasks의 목록
     */
    public List<Subtask> getMyTasks() {

        Member member = memberUtil.getMember();

        List<Subtask> tasks = subtaskRepository.findAllByMember(member).orElse(null);
        tasks.forEach(this::addInfo);

        return tasks ;
    }

    public Subtask get(Long seq) {
        QSubtask subtask = QSubtask.subtask;
        Subtask data = new JPAQueryFactory(em)
                .selectFrom(subtask)
                .where(subtask.seq.eq(seq))
                .leftJoin(subtask.todos)
                .fetchJoin()
                .fetchFirst();

        addInfo(data);

        return data;
    }

    private void addInfo(Subtask data) {
        String memberSeqs = data.getMemberSeqs();
        if (StringUtils.hasText((memberSeqs))) {
            QMember member = QMember.member;
            List<Long> seqs = Arrays.stream(memberSeqs.split(",")).filter(s -> !s.isBlank() && !s.equals("null")).map(Long::valueOf).toList();
            List<Member> members = (List<Member>)memberRepository.findAll(member.seq.in(seqs));
            data.setMembers(members);
        }
    }

}
