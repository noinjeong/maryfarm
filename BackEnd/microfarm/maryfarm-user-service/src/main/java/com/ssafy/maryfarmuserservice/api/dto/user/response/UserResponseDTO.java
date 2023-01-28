package com.ssafy.maryfarmuserservice.api.dto.user.response;

import com.ssafy.maryfarmuserservice.domain.Land;
import com.ssafy.maryfarmuserservice.domain.user.Tier;
import com.ssafy.maryfarmuserservice.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDTO {
    private String userId;
    private String nickname;
    private Tier tier;
    private String profilepath;
    private Land land;
    public static UserResponseDTO of(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.userId = user.getId();
        dto.nickname = user.getNickname();
        dto.tier = user.getTier();
        dto.profilepath = user.getProfilePath();
        dto.land = user.getLand();
        return dto;
    }
}
