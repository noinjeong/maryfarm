package com.ssafy.myfarm.api.dto.follow.response;

import com.ssafy.myfarm.domain.user.Follow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponseDTO {
    private String followid;

    public static FollowResponseDTO of(Follow follow) {
        FollowResponseDTO dto = new FollowResponseDTO();
        dto.followid = follow.getId();
        return dto;
    }
}
