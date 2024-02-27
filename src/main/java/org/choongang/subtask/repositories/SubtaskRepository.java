package org.choongang.subtask.repositories;

import org.choongang.subtask.entities.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
}
