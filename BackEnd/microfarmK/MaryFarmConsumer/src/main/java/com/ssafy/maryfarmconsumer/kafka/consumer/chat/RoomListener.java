package com.ssafy.maryfarmconsumer.kafka.consumer.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.kafka.constants.KafkaConstants;
import com.ssafy.maryfarmconsumer.query_dto.chat.RoomListView.RoomDTO;
import com.ssafy.maryfarmconsumer.query_dto.chat.RoomListView.RoomListDTO;
import com.ssafy.maryfarmconsumer.repository.chat.MessageRoomDTORepository;
import com.ssafy.maryfarmconsumer.repository.chat.RoomListDTORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoomListener {

    private final RoomListDTORepository roomListDTORepository;

    @KafkaListener(
            topics = "chatdb-room",
            groupId = "RoomListView"
    )
    public void RoomListViewListen(String message) throws JsonProcessingException {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {
        });
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        /*
            sender 처리
         */
        RoomDTO receiverRoomDto = new RoomDTO(payload, "receiver");
        Optional<RoomListDTO> senderRoomListDto = roomListDTORepository.findByUserId((String) payload.get("sender_id"));
        if(senderRoomListDto.isPresent()) {
            senderRoomListDto.get().getRooms().add(receiverRoomDto);
            roomListDTORepository.save(senderRoomListDto.get());
        } else {
            RoomListDTO roomListDTO = new RoomListDTO();
            roomListDTO.setUserId((String) payload.get("sender_id"));
            roomListDTO.getRooms().add(receiverRoomDto);
            roomListDTORepository.save(roomListDTO);
        }
        /*
            receiver 처리
         */
        RoomDTO senderRoomDto = new RoomDTO(payload, "sender");
        Optional<RoomListDTO> receiverRoomListDto = roomListDTORepository.findByUserId((String) payload.get("receiver_id"));
        if(receiverRoomListDto.isPresent()) {
            receiverRoomListDto.get().getRooms().add(senderRoomDto);
            roomListDTORepository.save(receiverRoomListDto.get());
        } else {
            RoomListDTO roomListDTO = new RoomListDTO();
            roomListDTO.setUserId((String) payload.get("receiver_id"));
            roomListDTO.getRooms().add(senderRoomDto);
            roomListDTORepository.save(roomListDTO);
        }
    }
}
