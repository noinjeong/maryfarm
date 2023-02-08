package com.ssafy.maryfarmnotifyservice.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmnotifyservice.api.dto.notify.request.CreateNotifyRequestDTO;
import com.ssafy.maryfarmnotifyservice.client.dto.user.UserResponseDTO;
import com.ssafy.maryfarmnotifyservice.client.service.user.UserServiceClient;
import com.ssafy.maryfarmnotifyservice.domain.notify.Notify;
import com.ssafy.maryfarmnotifyservice.kafka.constants.KafkaConstants;
import com.ssafy.maryfarmnotifyservice.service.NotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageListener {

    private final NotifyService notifyService;
    private final UserServiceClient userServiceClient;
    @KafkaListener(
            topics = "notify",
            groupId = KafkaConstants.GROUP_ID
    )
    public void NotifyListen(String message) {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
            log.info(map.toString());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

//        CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
//        if (entity != null) {
//            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
//            repository.save(entity);
//        }
    }
    @KafkaListener(
            topics = "follow",
            groupId = KafkaConstants.GROUP_ID
    )
    public void FollowListen(String message) {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
            log.info(map.toString());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        // 알람 생성 시작
        Map<Object,Object> follow = (Map<Object, Object>) map.get("follow");
        Map<Object, Object> senderUser = (Map<Object, Object>) follow.get("senderUser");
        Map<Object, Object> receiverUser = (Map<Object, Object>) follow.get("receiverUser");
        String content = senderUser.get("nickname") + "님이 내 농장 이웃이 되었어요!";
        String receiverId = (String) receiverUser.get("id");
        notifyService.saveNotify("FollowRequest",content,receiverId);

        // 알람 생성 끝
//        CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
//        if (entity != null) {
//            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
//            repository.save(entity);
//        }
    }
    @KafkaListener(
            topics = "diary",
            groupId = KafkaConstants.GROUP_ID
    )
    public void DiaryListen(String message) {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
            log.info(map.toString());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        Map<Object, Object> diary = (Map<Object, Object>) map.get("diary");
        Map<Object, Object> plant = (Map<Object, Object>) diary.get("plant");
        String userId = (String) plant.get("userId");
        UserResponseDTO userDto = userServiceClient.searchUser(userId);
        String notifyContent = userDto.getNickname()+"님이 새로운 일지를 올렸어요!";
        List<UserResponseDTO> followerDto = userServiceClient.searchFollower(userId);
        for(UserResponseDTO u : followerDto) {
            notifyService.saveNotify("FollowerUpload",notifyContent,u.getUserId());
        }
        // 알람 생성 종료
    }
}
