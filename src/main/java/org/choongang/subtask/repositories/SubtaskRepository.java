package org.choongang.subtask.repositories;

import org.choongang.subtask.entities.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SubtaskRepository extends JpaRepository<Subtask, Long>, QuerydslPredicateExecutor<Subtask> {
}
