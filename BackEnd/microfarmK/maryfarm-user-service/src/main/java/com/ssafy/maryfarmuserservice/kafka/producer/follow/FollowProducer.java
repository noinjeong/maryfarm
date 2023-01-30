package com.ssafy.maryfarmuserservice.kafka.producer.follow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmuserservice.domain.user.Follow;
import com.ssafy.maryfarmuserservice.domain.user.User;
import com.ssafy.maryfarmuserservice.kafka.dto.Field;
import com.ssafy.maryfarmuserservice.kafka.dto.Schema;
import com.ssafy.maryfarmuserservice.kafka.dto.follow.FollowDTO;
import com.ssafy.maryfarmuserservice.kafka.dto.follow.FollowPayload;
import com.ssafy.maryfarmuserservice.kafka.dto.follow.KafkaFollow;
import com.ssafy.maryfarmuserservice.kafka.dto.user.KafkaUser;
import com.ssafy.maryfarmuserservice.kafka.dto.user.UserPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class FollowProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(new Field("string", true, "follow_id"),
            new Field("string", true, "sender_id"),
            new Field("string", true, "receiver_id"));

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("follow")
            .build();

    public Follow send(String topic, FollowDTO follow) {
        // 변수가 많아 생성자가 너무 길어져서 Builder로 깔끔하게 생성
        FollowPayload payload = FollowPayload.builder()
                .follow_id(follow.getFollow_id())
                .receiver_id(follow.getReceiver_id())
                .sender_id(follow.getSender_id())
                .build();
        KafkaFollow kafkaFollow = new KafkaFollow(schema, payload);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(kafkaFollow);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }
        kafkaTemplate.send(topic, jsonInString);
        return follow;
    }
}
