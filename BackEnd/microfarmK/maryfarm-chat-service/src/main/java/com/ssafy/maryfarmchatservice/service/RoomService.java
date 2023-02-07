package com.ssafy.maryfarmchatservice.service;

import com.ssafy.maryfarmchatservice.api.dto.room.response.SearchRoomResponseDTO;
import com.ssafy.maryfarmchatservice.domain.chat.Room;
import com.ssafy.maryfarmchatservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    public Room saveRoom(String senderId, String senderName, String senderProfilePath,
                         String receiverId, String receiverName, String receiverProfilePath) {
        Room room = Room.of(senderId, senderName, senderProfilePath, receiverId, receiverName, receiverProfilePath);
        return roomRepository.save(room);
    }

    public List<Room> findByUser(String userId) {
        return roomRepository.findRoomBySenderIdOrReceiverId(userId, userId);
    }
}
