package org.choongang.subtask.repositories;

import org.choongang.member.entities.Member;
import org.choongang.subtask.entities.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface SubtaskRepository extends JpaRepository<Subtask, Long>, QuerydslPredicateExecutor<Subtask> {

    Optional<List<Subtask>> findAllByMember (Member member);
}
