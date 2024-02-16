package org.choongang.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.BaseMember;
import org.choongang.member.entities.Member;

import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseMember {
    @Id @GeneratedValue
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager")
    private List<Member> member;    // 참여자

    @Column(length = 80, nullable = false)
    private String pName;    // 프로젝트 명

    @Lob
    private String description;    // 프로젝트 설명문

    private boolean isValidUserOnly;    // 회원 전용 서비스

}
