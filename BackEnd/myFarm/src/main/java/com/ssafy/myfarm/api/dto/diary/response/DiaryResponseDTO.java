package com.ssafy.myfarm.api.dto.diary.response;

import com.ssafy.myfarm.domain.diary.Diary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDTO {
    private String diaryid;
    private String  title;
    private String content;
    private int likes;

    public static DiaryResponseDTO of(Diary diary) {
        DiaryResponseDTO dto = new DiaryResponseDTO();
        dto.diaryid = diary.getId();
        dto.title = diary.getTitle();
        dto.content = diary.getContent();
        dto.likes = diary.getLikes();
        return dto;
    }
}
