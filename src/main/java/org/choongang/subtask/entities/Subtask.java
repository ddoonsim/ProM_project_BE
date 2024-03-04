package org.choongang.subtask.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.constants.BType;
import org.choongang.commons.constants.Status;
import org.choongang.commons.entities.BaseMember;
import org.choongang.member.entities.Member;
import org.choongang.project.entities.Project;
import org.choongang.todolist.entities.Todolist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Subtask extends BaseMember {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=65, nullable = false)
    private String gid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pSeq")
    private Project project;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_subtask")
    private List<Member> member = new ArrayList<>();    // 참여자

    @Column(length = 80, nullable = false)
    private String tName;

    @Lob
    private String description;

    private String bType = BType.TODOLIST.name();

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private Status status = Status.REQUEST;

    private LocalDate sDate;

    private LocalDate eDate;

    private boolean isNotice = false;

    @OneToMany(mappedBy = "subtask", fetch=FetchType.LAZY)
    private List<Todolist> todos = new ArrayList<>();

}
