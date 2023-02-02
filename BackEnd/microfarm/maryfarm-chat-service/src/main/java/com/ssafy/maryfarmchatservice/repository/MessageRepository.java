package com.ssafy.maryfarmchatservice.repository;

import com.ssafy.maryfarmchatservice.domain.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
