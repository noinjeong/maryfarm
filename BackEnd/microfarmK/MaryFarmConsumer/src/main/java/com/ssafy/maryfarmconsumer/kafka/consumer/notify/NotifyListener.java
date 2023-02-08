package com.ssafy.maryfarmconsumer.kafka.consumer.notify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.query_dto.AllNotifyView.AllNotifyDTO;
import com.ssafy.maryfarmconsumer.query_dto.AllNotifyView.NotifyDTO;
import com.ssafy.maryfarmconsumer.repository.AllNotifyView.AllNotifyDTORepository;
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
public class NotifyListener {

    private final AllNotifyDTORepository allNotifyDTORepository;
    @KafkaListener(
            topics = "notifydb-notify",
            groupId = "AllNotifyView"
    )
    public void AllNotifyViewListen(String message) throws JsonProcessingException {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        NotifyDTO notifyDTO = new NotifyDTO(payload);
        Optional<AllNotifyDTO> dto = allNotifyDTORepository.findByUserId((String) payload.get("user_id"));
        if(dto.isPresent()) {
            dto.get().getNotifies().add(notifyDTO);
            allNotifyDTORepository.save(dto.get());
        } else {
            AllNotifyDTO allNotifyDTO = new AllNotifyDTO();
            allNotifyDTO.setUserId((String) payload.get("user_id"));
            allNotifyDTO.getNotifies().add(notifyDTO);
            allNotifyDTORepository.save(allNotifyDTO);
        }
    }
}
