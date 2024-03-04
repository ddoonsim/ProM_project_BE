package org.choongang.todolist.repositories;

import org.choongang.subtask.entities.Subtask;
import org.choongang.todolist.entities.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface TodolistRepository extends JpaRepository<Todolist, Long>, QuerydslPredicateExecutor<Todolist> {

    List<Todolist> findBySubtask(Subtask subtask);
}
