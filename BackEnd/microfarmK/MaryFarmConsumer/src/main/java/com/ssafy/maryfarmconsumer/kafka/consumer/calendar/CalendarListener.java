package com.ssafy.maryfarmconsumer.kafka.consumer.calendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.query_dto.calendar.CalendarPerDayView.CalendarPerDayDTO;
import com.ssafy.maryfarmconsumer.repository.calendar.CalendarPerDayDTORepository;
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
public class CalendarListener {
    private final CalendarPerDayDTORepository calendarPerDayDTORepository;
    @KafkaListener(
            topics = "calendardb-calendar",
            groupId = "CalendarPerDay"
    )
    public void CalendarPerDayListen(String message) throws JsonProcessingException {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        Optional<CalendarPerDayDTO> dto = calendarPerDayDTORepository.findByPlantIdAndYearAndMonthAndDay(
                (String) payload.get("plant_id"), (Integer) payload.get("year"),
                (Integer) payload.get("month"), (Integer) payload.get("day"));
        if(dto.isPresent()) {
            dto.get().changeStatus(payload);
            calendarPerDayDTORepository.save(dto.get());
        } else {
            CalendarPerDayDTO calendarPerDayDTO = new CalendarPerDayDTO(payload);
            calendarPerDayDTORepository.save(calendarPerDayDTO);
        }
    }
}
