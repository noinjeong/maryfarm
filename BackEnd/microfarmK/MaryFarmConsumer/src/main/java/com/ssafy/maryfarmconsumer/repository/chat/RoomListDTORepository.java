package com.ssafy.maryfarmconsumer.repository.chat;

import com.ssafy.maryfarmconsumer.query_dto.chat.RoomListView.RoomListDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomListDTORepository extends MongoRepository<RoomListDTO, String> {
    Optional<RoomListDTO> findByUserId(String userId);
}
