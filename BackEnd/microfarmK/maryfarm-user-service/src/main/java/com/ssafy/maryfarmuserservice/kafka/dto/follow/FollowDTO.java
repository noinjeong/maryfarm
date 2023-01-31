package com.ssafy.maryfarmuserservice.kafka.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowDTO {
    private String follow_id;
    private String sender_id;
    private String receiver_id;
}
