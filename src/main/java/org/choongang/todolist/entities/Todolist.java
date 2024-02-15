package org.choongang.todolist.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.constants.Status;
import org.choongang.commons.entities.BaseMember;
import org.choongang.member.entities.Member;
import org.choongang.subtask.entities.Subtask;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Todolist extends BaseMember {

    @Id @GeneratedValue
    private Long seq;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subtask")
    private Subtask subtask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager")
    private Member member;

    @Lob
    private String content;

    private Status status = Status.REQUEST;
}
