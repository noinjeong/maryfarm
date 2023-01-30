package com.ssafy.maryfarmuserservice.kafka.dto.user;

import com.ssafy.maryfarmuserservice.domain.Land;
import com.ssafy.maryfarmuserservice.domain.user.Tier;
import lombok.Data;

@Data
public class KafkaUserDTO {
    private String command;
    private String id;
    private String nickname;
    private Tier tier;
    private Land land;
    private String profilePath;
}
