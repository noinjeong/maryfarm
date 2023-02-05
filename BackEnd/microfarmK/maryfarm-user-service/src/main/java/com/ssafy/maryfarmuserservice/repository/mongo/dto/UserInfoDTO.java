package com.ssafy.maryfarmuserservice.repository.mongo.dto;

import com.ssafy.maryfarmuserservice.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("userinfo")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoDTO {
    @Id
    private String _id;
    private String nickname;
    private String tier;
    private String profilepath;

    public static UserInfoDTO of(User user) {
        UserInfoDTO dto = new UserInfoDTO();
        dto._id = user.getId();
        dto.nickname = user.getNickname();
        dto.tier = user.getTier().name();
        dto.profilepath = user.getProfilePath();
        return dto;
    }
}
