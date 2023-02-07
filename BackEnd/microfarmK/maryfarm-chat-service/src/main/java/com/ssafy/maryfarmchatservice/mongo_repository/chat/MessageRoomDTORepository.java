package com.ssafy.maryfarmchatservice.mongo_repository.chat;

import com.ssafy.maryfarmchatservice.api.dto.query.MessageRoomView.MessageRoomDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MessageRoomDTORepository extends MongoRepository<MessageRoomDTO, String> {
    Optional<MessageRoomDTO> findByRoomId(String roomId);
}
