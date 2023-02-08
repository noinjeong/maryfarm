package com.ssafy.myfarm.service;

import com.ssafy.myfarm.domain.chat.Room;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.RoomRepository;
import com.ssafy.myfarm.repository.UserRepository;
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
    private final UserRepository userRepository;

    public Room saveRoom(String senderId, String receiverId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);
        Room room = Room.of(sender.get(), receiver.get());
        return roomRepository.save(room);
    }
}
