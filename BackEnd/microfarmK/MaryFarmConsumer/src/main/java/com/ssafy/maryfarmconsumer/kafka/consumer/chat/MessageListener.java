package com.ssafy.maryfarmconsumer.kafka.consumer.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.kafka.constants.KafkaConstants;
import com.ssafy.maryfarmconsumer.query_dto.chat.MessageRoomView.MessageDTO;
import com.ssafy.maryfarmconsumer.query_dto.chat.MessageRoomView.MessageRoomDTO;
import com.ssafy.maryfarmconsumer.query_dto.chat.RoomListView.RoomDTO;
import com.ssafy.maryfarmconsumer.query_dto.chat.RoomListView.RoomListDTO;
import com.ssafy.maryfarmconsumer.repository.chat.MessageRoomDTORepository;
import com.ssafy.maryfarmconsumer.repository.chat.RoomListDTORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageListener {

    private final MessageRoomDTORepository messageRoomDTORepository;
    private final RoomListDTORepository roomListDTORepository;
    @KafkaListener(
            topics = "chatdb-message",
            groupId = "MessagesPerRoom"
    )
    public void MessagePerRoomListen(String message) throws JsonProcessingException {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        MessageDTO messageDTO = new MessageDTO(payload);
        Optional<MessageRoomDTO> dto = messageRoomDTORepository.findByRoomId((String) payload.get("room_id"));
        if(dto.isPresent()) {
            dto.get().getMessages().add(messageDTO);
            messageRoomDTORepository.save(dto.get());
        } else {
            MessageRoomDTO messageRoomDTO = new MessageRoomDTO();
            messageRoomDTO.setRoomId((String) payload.get("room_id"));
            messageRoomDTO.getMessages().add(messageDTO);
            messageRoomDTORepository.save(messageRoomDTO);
        }
    }

    @KafkaListener(
            topics = "chatdb-message",
            groupId = "latestMassageToRoomData"
    )
    public void latestMassageToRoomDataListen(String message) throws JsonProcessingException {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {
        });
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        /*
            sender의 RoomList에 최근 메시지 추가
         */
        Optional<RoomListDTO> roomDto = roomListDTORepository.findByUserId((String) payload.get("user_id"));
        List<RoomDTO> myRooms = roomDto.get().getRooms();
        String opponentId = null;
        for(RoomDTO r : myRooms) {
            if(r.getRoomId().equals((String) payload.get("room_id"))) {
                r.setLatestMessage((String) payload.get("content"));
                LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("timestamp")), TimeZone.getDefault().toZoneId());
                r.setLatestTimestamp(timestamp);
                opponentId = r.getOpponentId();
            }
        }
        roomListDTORepository.save(roomDto.get());
        Optional<RoomListDTO> oppoRoomDto = roomListDTORepository.findByUserId(opponentId);
        List<RoomDTO> oppoRooms = oppoRoomDto.get().getRooms();
        for(RoomDTO r : oppoRooms) {
            if(r.getRoomId().equals((String) payload.get("room_id"))) {
                r.setLatestMessage((String) payload.get("content"));
                LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("timestamp")), TimeZone.getDefault().toZoneId());
                r.setLatestTimestamp(timestamp);
            }
        }
        roomListDTORepository.save(oppoRoomDto.get());
    }
}
