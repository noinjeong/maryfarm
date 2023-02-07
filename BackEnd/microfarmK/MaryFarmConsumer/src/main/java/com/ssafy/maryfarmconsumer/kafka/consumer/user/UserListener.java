package com.ssafy.maryfarmconsumer.kafka.consumer.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.query_dto.chat.MessageRoomView.MessageDTO;
import com.ssafy.maryfarmconsumer.query_dto.chat.MessageRoomView.MessageRoomDTO;
import com.ssafy.maryfarmconsumer.query_dto.plant.HomeDiaryImageDTO;
import com.ssafy.maryfarmconsumer.query_dto.user.FirstHomeView.FirstHomeViewDTO;
import com.ssafy.maryfarmconsumer.repository.plant.HomeDiaryImageDTORepository;
import com.ssafy.maryfarmconsumer.repository.user.FirstHomeViewDTORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {
    private final FirstHomeViewDTORepository firstHomeViewDTORepository;
    private final HomeDiaryImageDTORepository homeDiaryImageDTORepository;
    @KafkaListener(
            topics = "userdb-user",
            groupId = "FirstHomeView"
    )
    public void FirstHomeViewListen(String message) throws JsonProcessingException {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        Optional<FirstHomeViewDTO> myHomeViewDTO = firstHomeViewDTORepository.findByUserId((String) payload.get("user_id"));
        if(!myHomeViewDTO.isPresent()) {
            FirstHomeViewDTO firstHomeViewDTO = new FirstHomeViewDTO();
            firstHomeViewDTO.setUserId((String) payload.get("user_id"));
            firstHomeViewDTO.setLatestSystemNotify("처음 방문하셨군요!");
            List<HomeDiaryImageDTO> allHomeDiaryImageDto = homeDiaryImageDTORepository.findAll();
            firstHomeViewDTO.getDiaries().addAll(allHomeDiaryImageDto);
            firstHomeViewDTORepository.save(firstHomeViewDTO);
        }
    }
}
