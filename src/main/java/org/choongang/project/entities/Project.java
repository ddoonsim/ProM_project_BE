package org.choongang.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.BaseMember;
import org.choongang.member.entities.Member;

@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseMember {
    @Id @GeneratedValue
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager")
    private Member member;

    @Column(length = 80, nullable = false)
    private String pName;

    private String description;

    private boolean isValidUserOnly;

}
