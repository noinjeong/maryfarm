package com.ssafy.maryfarmuserservice.kafka.dto.follow;

import com.ssafy.maryfarmuserservice.kafka.dto.Schema;
import com.ssafy.maryfarmuserservice.kafka.dto.user.UserPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaFollow {
    private Schema schema;
    private FollowPayload payload;
}
