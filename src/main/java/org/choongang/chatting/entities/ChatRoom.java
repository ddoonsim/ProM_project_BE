package org.choongang.chatting.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.project.entities.Project;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ChatRoom extends Base {
    @Id @GeneratedValue
    private Long roomNo;

    @NotNull
    @Column(length=45, nullable = false)
    private String roomNm;


    @OneToOne
    @JoinColumn(name="projectSeq")
    private Project project;

    private int capacity = 2; // 방 인원수
}
