package com.ssafy.maryfarmplantservice.api.dto.diary.response;

import com.ssafy.myfarm.domain.diary.Diary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryToHomeResponseDTO {
    private String plantId;
    private String title;
    private String imagepath;

    public static DiaryToHomeResponseDTO of(Diary diary) {
        DiaryToHomeResponseDTO dto = new DiaryToHomeResponseDTO();
        dto.plantid = diary.getPlant().getId();
        dto.title = diary.getPlant().getTitle();
        dto.imagepath = diary.getImagePath();
        return dto;
    }
}
