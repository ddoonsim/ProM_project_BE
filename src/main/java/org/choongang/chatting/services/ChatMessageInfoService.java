package org.choongang.chatting.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.choongang.chatting.entities.ChatHistory;
import org.choongang.chatting.entities.QChatHistory;
import org.choongang.chatting.repositories.ChatHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageInfoService {
    private final ChatHistoryRepository chatHistoryRepository;
    private final EntityManager em;

    public List<ChatHistory> getList(Long roomNo) {

        QChatHistory chatHistory = QChatHistory.chatHistory;
        BooleanBuilder andBuilder = new BooleanBuilder();

        andBuilder.and(chatHistory.chatRoom.roomNo.eq(roomNo));

        PathBuilder<ChatHistory> pathBuilder = new PathBuilder<>(ChatHistory.class, "chatHistory");


        List<ChatHistory> items = new JPAQueryFactory(em)
                .selectFrom(chatHistory)
                .leftJoin(chatHistory.member)
                .fetchJoin()
                .where(andBuilder)
                .orderBy(new OrderSpecifier(Order.ASC, pathBuilder.get("createdAt")))
                .fetch();

        return items;
    }

}
