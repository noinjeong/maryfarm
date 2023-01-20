package com.ssafy.myfarm.api.dto.diary.response;

import com.ssafy.myfarm.domain.diary.Diary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupedDiaryForSearchDTO {
    private String diaryid;
    private String imagepath;

    public static GroupedDiaryForSearchDTO of(Diary diary) {
        GroupedDiaryForSearchDTO dto = new GroupedDiaryForSearchDTO();
        dto.diaryid = diary.getId();
        dto.imagepath = diary.getImagePath();
        return dto;
    }
}
