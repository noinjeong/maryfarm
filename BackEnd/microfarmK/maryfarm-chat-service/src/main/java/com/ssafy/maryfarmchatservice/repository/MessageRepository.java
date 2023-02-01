package com.ssafy.maryfarmchatservice.repository;

import com.ssafy.maryfarmchatservice.domain.chat.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    public List<Message> findByRoomIdOrderByTimestamp(String roomId, Pageable pageable);

    public List<Message> findByRoomId(String roomId);
}
