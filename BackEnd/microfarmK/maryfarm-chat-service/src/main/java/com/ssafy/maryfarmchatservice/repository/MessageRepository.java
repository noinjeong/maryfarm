package com.ssafy.maryfarmchatservice.repository;

import com.ssafy.maryfarmchatservice.domain.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, String> {
    public Message findTopByTimestampAndAndRoomId(String roomId);
}
