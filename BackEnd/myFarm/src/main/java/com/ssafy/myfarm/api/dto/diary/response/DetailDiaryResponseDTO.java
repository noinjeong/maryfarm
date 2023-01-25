package com.ssafy.myfarm.api.dto.diary.response;

import com.ssafy.myfarm.api.dto.plant.response.PlantResponseDTO;
import com.ssafy.myfarm.api.dto.tag.TagResponseDTO;
import com.ssafy.myfarm.domain.diary.Diary;
import com.ssafy.myfarm.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDiaryResponseDTO {
    private String diaryid;
    private PlantResponseDTO plant;
    private String content;
    private Integer likes;
    private String imagepath;
    private List<TagResponseDTO> taglist = new ArrayList<>();

    public static DetailDiaryResponseDTO of(Diary diary) {
        DetailDiaryResponseDTO dto = new DetailDiaryResponseDTO();
        dto.diaryid = diary.getId();
        dto.plant = PlantResponseDTO.of(diary.getPlant());
        dto.content = diary.getContent();
        dto.likes = diary.getLikes();
        dto.imagepath = diary.getImagePath();
        for(Tag t : diary.getTags()) {
            dto.taglist.add(TagResponseDTO.from(t));
        }
        return dto;
    }
}
