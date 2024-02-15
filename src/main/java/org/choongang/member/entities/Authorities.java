package org.choongang.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.constants.MemberType;

@Data
@Entity
public class Authorities {
    @Id
    @GeneratedValue
    private Long seq;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_seq")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length=15, nullable = false)
    private MemberType MemberType;
}
