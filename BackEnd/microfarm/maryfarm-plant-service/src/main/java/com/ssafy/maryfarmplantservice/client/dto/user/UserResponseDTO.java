package com.ssafy.maryfarmplantservice.client.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDTO {
    private String userid;
    private String nickname;
    private String profilepath;
}
