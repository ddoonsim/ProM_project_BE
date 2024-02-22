package org.choongang.chatting.repositories;

import org.choongang.chatting.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, QuerydslPredicateExecutor<ChatRoom> {
    Optional<ChatRoom> findByProjectSeq(Long ProjectSeq);
}