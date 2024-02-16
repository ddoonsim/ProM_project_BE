package org.choongang.project.repositories;

import org.choongang.project.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProjectRepository extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project> {
}
