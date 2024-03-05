package org.choongang.subtask.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="memberSeq")
    private Member member;

    //@ManyToMany(fetch = FetchType.EAGER)
    //@JoinTable(name = "member_subtask")
    //private List<Member> member = new ArrayList<>();    // 참여자
    @Column(length=100)
    private String memberSeqs;

    @Column(length = 80, nullable = false)
    private String tName;

    @Lob
    private String description;

    private String bType = BType.TODOLIST.name();

    @Enumerated(EnumType.STRING)
    @Column(length=10)
    private Status status = Status.REQUEST;

    private LocalDate sDate;

    private LocalDate eDate;

    private boolean isNotice = false;

    @OneToMany(mappedBy = "subtask", fetch=FetchType.LAZY)
    private List<Todolist> todos = new ArrayList<>();

    @Transient
    private List<Member> members;

}
