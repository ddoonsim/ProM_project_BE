package org.choongang.todolist.repositories;

import org.choongang.todolist.entities.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TodolistRepository extends JpaRepository<Todolist, Long>, QuerydslPredicateExecutor<Todolist> {
}
