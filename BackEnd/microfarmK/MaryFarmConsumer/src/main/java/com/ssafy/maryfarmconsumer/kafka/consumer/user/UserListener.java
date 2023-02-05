package com.ssafy.maryfarmconsumer.kafka.consumer.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.domain.user.User;
import com.ssafy.maryfarmconsumer.kafka.constants.KafkaConstants;
import com.ssafy.maryfarmconsumer.kafka.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {
    private final MongoTemplate mongoTemplate;
    @KafkaListener(
            topics = "user",
            groupId = "user-info-group"
    )
    public void UserInfoListen(String message) {
        log.info("Kafka Message: ->" + message);
        User user = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            user = mapper.readValue(message, User.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        mongoTemplate.insert(UserInfoDTO.of(user));
    }
    @KafkaListener(
            topics = "user",
            groupId = "user-homeview-group"
    )
    public void UserHomeViewListen(String message) {
        log.info("Kafka Message: ->" + message);
        User user = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            user = mapper.readValue(message, User.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        mongoTemplate.insert(UserInfoDTO.of(user));
    }
}
