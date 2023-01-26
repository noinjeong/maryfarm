package com.ssafy.myfarm.api.dto.user.response;

import com.ssafy.myfarm.domain.Land;
import com.ssafy.myfarm.domain.user.Tier;
import com.ssafy.myfarm.domain.user.User;
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
    private Tier tier;
    private String profilepath;
    private Land land;
    public static UserResponseDTO of(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.userid = user.getId();
        dto.nickname = user.getNickname();
        dto.tier = user.getTier();
        dto.profilepath = user.getProfilePath();
        dto.land = user.getLand();
        return dto;
    }
}
