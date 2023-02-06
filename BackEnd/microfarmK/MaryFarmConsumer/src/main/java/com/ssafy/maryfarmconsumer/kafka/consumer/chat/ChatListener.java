package com.ssafy.maryfarmconsumer.kafka.consumer.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.kafka.constants.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatListener {

    private final SimpMessagingTemplate template;
//    @KafkaListener(
//            topics = "message",
//            groupId = KafkaConstants.GROUP_ID
//    )
//    public void MessageListen(String message) {
//        log.info("Kafka Message: ->" + message);
//
//        Map<Object, Object> map = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {
//            });
//            log.info(map.toString());
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }
//        Map<Object, Object> messageEntity = (Map<Object, Object>) map.get("message");
//        Map<Object, Object> room = (Map<Object, Object>) messageEntity.get("room");
//        System.out.println("sending via kafka listener..");
//        System.out.println("/topic/group/" + room.get("id"));
//        template.convertAndSend("/topic/group/" + room.get("id"), message);
//    }
//
//    @KafkaListener(
//            topics = "room",
//            groupId = KafkaConstants.GROUP_ID
//    )
//    public void RoomListen(String message) {
//        log.info("Kafka Message: ->" + message);
//
//        Map<Object, Object> map = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {
//            });
//            log.info(map.toString());
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }
//    }
}
