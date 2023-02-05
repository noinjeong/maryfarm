package com.ssafy.maryfarmconsumer.kafka.dto;

import com.ssafy.maryfarmconsumer.domain.user.User;
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
    private String nickName;
    private String tier;
    private String profilePath;

    public static UserInfoDTO of(User user) {
        UserInfoDTO dto = new UserInfoDTO();
        dto._id = user.getId();
        dto.nickName = user.getNickname();
        dto.tier = user.getTier().name();
        dto.profilePath = user.getProfilePath();
        return dto;
    }
}
