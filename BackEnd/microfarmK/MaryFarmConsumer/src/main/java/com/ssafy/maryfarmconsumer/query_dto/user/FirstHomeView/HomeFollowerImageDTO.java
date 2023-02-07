package com.ssafy.maryfarmconsumer.query_dto.user.FirstHomeView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeFollowerImageDTO {
    private String userId;
    private String userName;
    private String profilePath;
    private String latestDiaryImagePath;

    public HomeFollowerImageDTO(Map<Object, Object> payload) {
        this.userId = (String) payload.get("receiver_id");
        this.userName = (String) payload.get("receiver_name");
        this.profilePath = (String) payload.get("receiver_profile_path");
    }
}
