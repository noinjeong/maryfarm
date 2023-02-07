package com.ssafy.maryfarmconsumer.kafka.consumer.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.query_dto.plant.HomeDiaryImageDTO;
import com.ssafy.maryfarmconsumer.query_dto.user.FirstHomeView.FirstHomeViewDTO;
import com.ssafy.maryfarmconsumer.query_dto.user.FirstHomeView.HomeFollowerImageDTO;
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
public class FollowListener {
    private final FirstHomeViewDTORepository firstHomeViewDTORepository;
    private final HomeDiaryImageDTORepository homeDiaryImageDTORepository;

    @KafkaListener(
            topics = "userdb-follow",
            groupId = "AddingFirstHomeViewFollower"
    )
    public void FirstHomeViewListen(String message) throws JsonProcessingException {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        Optional<FirstHomeViewDTO> dto = firstHomeViewDTORepository.findByUserId((String) payload.get("sender_id"));
        HomeFollowerImageDTO homeFollowerImageDTO = new HomeFollowerImageDTO(payload);
        Optional<HomeDiaryImageDTO> followerDiaryImageDto = homeDiaryImageDTORepository.findTopByUserIdOrderByCreatedDateDesc((String) payload.get("receiver_id"));
        homeFollowerImageDTO.setLatestDiaryImagePath(followerDiaryImageDto.get().getLatestDiaryImagePath());
        dto.get().getFollowers().add(homeFollowerImageDTO);
        firstHomeViewDTORepository.save(dto.get());
    }
}
