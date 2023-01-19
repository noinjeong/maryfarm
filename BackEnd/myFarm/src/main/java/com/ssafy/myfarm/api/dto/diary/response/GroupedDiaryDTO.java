package com.ssafy.myfarm.api.dto.diary.response;

import com.ssafy.myfarm.domain.diary.Diary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupedDiaryDTO {
    private String diaryid;
    private String imagepath;

    public static GroupedDiaryDTO of(Diary diary) {
        GroupedDiaryDTO dto = new GroupedDiaryDTO();
        dto.diaryid = diary.getId();
        dto.imagepath = diary.getImagePath();
        return dto;
    }
}
