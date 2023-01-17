package com.ssafy.myfarm.api.dto.diary.response;

import com.ssafy.myfarm.domain.FileInfo;
import com.ssafy.myfarm.domain.diary.Diary;
import com.ssafy.myfarm.domain.plant.Plant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDTO {
    private Long id;
    private String  title;
    private String content;
    private int likes;

    public static DiaryResponseDTO of(Diary diary) {
        DiaryResponseDTO dto = new DiaryResponseDTO();
        dto.id = diary.getId();
        dto.title = diary.getTitle();
        dto.content = diary.getContent();
        dto.likes = diary.getLikes();
        return dto;
    }
}
