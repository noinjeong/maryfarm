package com.ssafy.maryfarmconsumer.kafka.consumer.plant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.query_dto.FirstHomeView.HomeDiaryImageDTO;
import com.ssafy.maryfarmconsumer.repository.FirstHomeView.HomeDiaryImageDTORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiaryListener {
    private final HomeDiaryImageDTORepository homeDiaryImageDTORepository;

    @KafkaListener(
            topics = "plantdb-diary",
            groupId = "HomeDiaryImage"
    )
    public void HomeDiaryImageListen(String message) throws JsonProcessingException {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        Optional<HomeDiaryImageDTO> dto = homeDiaryImageDTORepository.findByPlantId((String) payload.get("plant_id"));
        if(dto.isPresent()) {
            String prevDiaryImagePath = dto.get().getLatestDiaryImagePath();
            dto.get().setLatestDiaryImagePath((String) payload.get("image_path"));
            dto.get().setContent((String) payload.get("content"));
            LocalDateTime createdDate = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("created_date")), TimeZone.getDefault().toZoneId());
            dto.get().setCreatedDate(createdDate);
            dto.get().getOtherDiaryImagePath().add(prevDiaryImagePath);
            homeDiaryImageDTORepository.save(dto.get());
        } else {
            HomeDiaryImageDTO homeDiaryImageDTO = new HomeDiaryImageDTO(payload);
            homeDiaryImageDTORepository.save(homeDiaryImageDTO);
        }
    }


}
