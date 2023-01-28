package com.ssafy.maryfarmchatservice.service;

import com.ssafy.maryfarmchatservice.domain.chat.Room;
import com.ssafy.maryfarmchatservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;

    public Room saveRoom(String senderId, String receiverId) {
        Room room = Room.of(senderId, receiverId);
        return roomRepository.save(room);
    }
}
