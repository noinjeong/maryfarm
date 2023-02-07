package com.ssafy.maryfarmconsumer.repository.chat;

import com.ssafy.maryfarmconsumer.query_dto.chat.MessageRoomView.MessageRoomDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MessageRoomDTORepository extends MongoRepository<MessageRoomDTO, String> {
    Optional<MessageRoomDTO> findByRoomId(String roomId);
}
