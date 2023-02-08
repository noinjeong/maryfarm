package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
