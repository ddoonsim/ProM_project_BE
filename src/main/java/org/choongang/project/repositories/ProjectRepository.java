package org.choongang.project.repositories;

import org.choongang.project.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project> {

    Optional<List<Project>> findByMemberSeq(Long MemberSeq);  // 회원 별 참여 중인 프로젝트 목록
}
