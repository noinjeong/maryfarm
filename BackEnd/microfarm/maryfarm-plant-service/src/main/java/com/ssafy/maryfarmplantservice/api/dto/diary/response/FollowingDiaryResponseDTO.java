package com.ssafy.maryfarmplantservice.api.dto.diary.response;

import com.ssafy.myfarm.api.dto.user.response.UserResponseDTO;
import com.ssafy.myfarm.domain.diary.Diary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowingDiaryResponseDTO {
    private UserResponseDTO user;
    private String imagepath;
    public static FollowingDiaryResponseDTO of(Diary diary) {
        FollowingDiaryResponseDTO dto = new FollowingDiaryResponseDTO();
        dto.user = UserResponseDTO.of(diary.getPlant().getUser());
        dto.imagepath = diary.getImagePath();
        return dto;
    }
}
