package com.ssafy.maryfarmnotifyservice.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmnotifyservice.client.dto.plant.DiaryResponseDTO;
import com.ssafy.maryfarmnotifyservice.client.dto.plant.PlantResponseDTO;
import com.ssafy.maryfarmnotifyservice.client.dto.user.UserResponseDTO;
import com.ssafy.maryfarmnotifyservice.client.service.plant.PlantServiceClient;
import com.ssafy.maryfarmnotifyservice.client.service.user.UserServiceClient;
import com.ssafy.maryfarmnotifyservice.kafka.constants.KafkaConstants;
import com.ssafy.maryfarmnotifyservice.service.NotifyCService;
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

    private final NotifyCService notifyCService;
    private final UserServiceClient userServiceClient;
    private final PlantServiceClient plantServiceClient;

    @KafkaListener(
            topics = "follow",
            groupId = "notify-follow-accept"
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
//         알람 생성 시작
        Map<Object, Object> senderUser = (Map<Object, Object>) map.get("senderUser");
        Map<Object, Object> receiverUser = (Map<Object, Object>) map.get("receiverUser");
        String content = senderUser.get("userName") + "님이 내 농장 이웃이 되었어요!";
        String receiverId = (String) receiverUser.get("id");
        String senderId = (String) senderUser.get("id");
        notifyCService.saveNotify("FollowRequest",content,receiverId,senderId,null,null);
//         알람 생성 끝
    }
    @KafkaListener(
            topics = "diary",
            groupId = "notify-diary-accept"
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

        Map<Object, Object> plant = (Map<Object, Object>) map.get("plant");
        String content = map.get("userName")+"님이 새로운 일지를 올렸어요!";
        String userId = (String) plant.get("userId");
        String plantId = (String) plant.get("id");
        String diaryId = (String) map.get("id");
        List<UserResponseDTO> followerDto = userServiceClient.searchFollower(userId);
        for(UserResponseDTO u : followerDto) {
            notifyCService.saveNotify("FollowerUpload",content,u.getUserId(),null,plantId,diaryId);
        }
        // 알람 생성 종료
    }

    @KafkaListener(
            topics = "diary_like",
            groupId = "notify-diary-accept"
    )
    public void DiaryLikeListen(String message) {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
            log.info(map.toString());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        String senderName = (String) map.get("userName");
        String senderId = (String) map.get("userId");
        String diaryId = (String) map.get("diaryId");
        DiaryResponseDTO diaryResponseDTO = plantServiceClient.DiarySearch(diaryId);
        String plantId = diaryResponseDTO.getPlantId();
        String receiverId = diaryResponseDTO.getUserId();
        String content = senderName + "님이 내 "+ diaryResponseDTO.getTitle() +" 일지를 좋아합니다!";
        notifyCService.saveNotify("DiaryLike",content,receiverId,null,plantId,diaryId);
    }
}
