package com.ssafy.maryfarmconsumer.kafka.consumer.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {
    private final MongoTemplate mongoTemplate;
//    @KafkaListener(
//            topics = "user",
//            groupId = "user-info-group"
//    )
//    public void UserInfoListen(String message) {
//        log.info("Kafka Message: ->" + message);
//        User user = null;
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            user = mapper.readValue(message, User.class);
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }
//        mongoTemplate.insert(UserInfoDTO.of(user));
//    }
//    @KafkaListener(
//            topics = "user",
//            groupId = "user-homeview-group"
//    )
//    public void UserHomeViewListen(String message) {
//        log.info("Kafka Message: ->" + message);
//        User user = null;
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            user = mapper.readValue(message, User.class);
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }
//        mongoTemplate.insert(UserInfoDTO.of(user));
//    }
}
