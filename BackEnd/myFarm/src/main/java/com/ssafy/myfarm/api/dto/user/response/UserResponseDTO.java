package com.ssafy.myfarm.api.dto.user.response;

import com.ssafy.myfarm.domain.FileInfo;
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
    private Long id;
    private String email;
    private String nickname;
    private String birthday;
    private Tier tier;
    private FileInfo fileinfo;
    public static UserResponseDTO from (User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.id = user.getId();
        dto.email = user.getEmail();
        dto.nickname = user.getNickname();
        dto.birthday = user.getBirthday();
        dto.tier = user.getTier();
        dto.fileinfo = user.getFileInfo();
        return dto;
    }
}
