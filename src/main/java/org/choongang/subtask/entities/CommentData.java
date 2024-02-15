package org.choongang.subtask.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.member.entities.Member;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class CommentData {
    @Id @GeneratedValue
    private Long seq;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subtaskSeq")
    private Subtask subtask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberSeq")
    private Member member;

    @Lob
    @Column(nullable = false)
    private String content; // 댓글 내용

}
