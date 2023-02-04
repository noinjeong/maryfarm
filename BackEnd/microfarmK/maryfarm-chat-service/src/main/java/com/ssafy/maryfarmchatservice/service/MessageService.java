package com.ssafy.maryfarmchatservice.service;

import com.ssafy.maryfarmchatservice.domain.chat.Message;
import com.ssafy.maryfarmchatservice.domain.chat.Room;
import com.ssafy.maryfarmchatservice.repository.MessageRepository;
import com.ssafy.maryfarmchatservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;


    public Message searchLatestMessage(String roomId) {
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Message> list = messageRepository.findByRoomIdOrderByTimestamp(roomId, pageRequest);
        if(list.isEmpty()) return null;
        else return list.get(0);
    }

    @Transactional
    public Message saveMessage(String roomId, String userId, String content) {
        Optional<Room> room = roomRepository.findById(roomId);
        Message message = Message.of(room.get(), userId, content, LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> findMessageByRoom(String roomId) {
        return messageRepository.findByRoomId(roomId);
    }
}
