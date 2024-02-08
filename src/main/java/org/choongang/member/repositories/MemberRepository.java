package org.choongang.member.repositories;

import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

    Optional<Member> findByEmail(String email);

    /**
     * 가입된 이메일이 존재하는지
     * 중복 O(이미 존재) : true
     * 중복 X : false
     */
    default boolean exists(String email) {
        return exists(QMember.member.email.eq(email));
    }
}
