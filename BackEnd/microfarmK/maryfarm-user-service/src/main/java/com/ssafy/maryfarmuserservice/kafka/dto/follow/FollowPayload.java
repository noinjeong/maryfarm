package com.ssafy.maryfarmuserservice.kafka.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowPayload {
    private String follow_id;
    private String sender_id;
    private String receiver_id;
}
