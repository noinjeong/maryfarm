package com.ssafy.maryfarmuserservice.kafka.dto.follow;

import com.ssafy.maryfarmuserservice.domain.user.User;
import lombok.Data;

@Data
public class KafkaFollowDTO {
    private String command;
    private String id;
    private User senderUser;
    private User receiverUser;
}
