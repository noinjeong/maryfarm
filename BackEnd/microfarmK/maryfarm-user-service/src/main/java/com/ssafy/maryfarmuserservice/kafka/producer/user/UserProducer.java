package com.ssafy.maryfarmuserservice.kafka.producer.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmuserservice.domain.user.User;
import com.ssafy.maryfarmuserservice.kafka.dto.Field;
import com.ssafy.maryfarmuserservice.kafka.dto.user.KafkaUser;
import com.ssafy.maryfarmuserservice.kafka.dto.user.Payload;
import com.ssafy.maryfarmuserservice.kafka.dto.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(new Field("string", true, "user_id"),
            new Field("string", true, "nickname"),
            new Field("string", true, "tier"),
            new Field("string", true, "latitude"),
            new Field("string", true, "longitude"),
            new Field("string", true, "profilePath"));

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("user")
            .build();

    public User send(String topic, User user) {
        // 변수가 많아 생성자가 너무 길어져서 Builder로 깔끔하게 생성
        Payload payload = Payload.builder()
                .user_id(user.getId())
                .nickname(user.getNickname())
                .tier(user.getTier().name())
                .latitude(null)
                .longitude(null)
                .profilePath(user.getProfilePath())
                .build();
        KafkaUser kafkaUser = new KafkaUser(schema, payload);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(kafkaUser);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }
        log.info("Order Producer sent data from the Order microservice: " + jsonInString);
        kafkaTemplate.send(topic, jsonInString);
        return user;
    }
}
