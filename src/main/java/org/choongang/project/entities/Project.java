package org.choongang.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.chatting.entities.ChatRoom;
import org.choongang.commons.entities.BaseMember;
import org.choongang.member.entities.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends BaseMember {
    @Id @GeneratedValue
    private Long seq;

//    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_project")
    private List<Member> member = new ArrayList<>();    // 참여자

    @Column(length = 80, nullable = false)
    private String pName;    // 프로젝트 명

    @Lob
    private String description;    // 프로젝트 설명문

    private boolean isValidUserOnly;    // 회원 전용 서비스

    @Override
    public String toString() {
        return "Project{" +
                "seq=" + seq +
                ", pName='" + pName + '\'' +
                ", description='" + description + '\'' +
                ", isValidUserOnly=" + isValidUserOnly +
                '}';
    }

    @OneToOne
    @JoinColumn(name = "roomNo")
    private ChatRoom chatRoom;
}
