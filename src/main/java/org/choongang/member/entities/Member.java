package org.choongang.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.constants.MemberType;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;
import org.choongang.project.entities.Project;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Member extends Base {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=65, nullable = false)
    private String gid;

    @Column(length=40, nullable = false)
    private String name;

    @Column(length=65, unique = true, nullable = false)
    private String email;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=15)
    private String mobile;

    @Transient
    private FileInfo profileImage;

    @Enumerated(EnumType.STRING)
    @Column(length=30, nullable = false)
    private MemberType type = MemberType.USER;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_project")
    private List<Project> project = new ArrayList<>();    // 참여 중인 프로젝트

}
