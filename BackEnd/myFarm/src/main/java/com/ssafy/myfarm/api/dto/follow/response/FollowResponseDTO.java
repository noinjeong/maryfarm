package com.ssafy.myfarm.api.dto.follow.response;

import com.ssafy.myfarm.domain.user.Follow;
import com.ssafy.myfarm.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponseDTO {
    private Long id;

    public static FollowResponseDTO of(Follow follow) {
        FollowResponseDTO dto = new FollowResponseDTO();
        dto.id = follow.getId();
        return dto;
    }
}
