package org.choongang.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
    @ToString.Exclude
    @JsonIgnore
    private Subtask subtask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager")
    @ToString.Exclude
    @JsonIgnore
    private Member member;

    @Lob
    private String content;

    private boolean done;
}
