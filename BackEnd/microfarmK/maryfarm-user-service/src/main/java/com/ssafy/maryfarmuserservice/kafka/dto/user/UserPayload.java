package com.ssafy.maryfarmuserservice.kafka.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPayload {
    private String user_id;
    private String nickname;
    private String tier;
    private String latitude;
    private String longitude;
    private String profilePath;
}
