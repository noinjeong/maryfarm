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
public class DiarySearchResponseDTO {
    private PlantResponseDTO plant;
    private String content;
    private Integer likes;
    private String imagepath;

    private List<TagResponseDTO> taglist = new ArrayList<>();

    private List<GroupedDiaryForSearchDTO> diarygroup = new ArrayList<>();

    public static DiarySearchResponseDTO of(Diary diary, List<Diary> diarygroup) {
        DiarySearchResponseDTO dto = new DiarySearchResponseDTO();
        dto.plant = PlantResponseDTO.of(diary.getPlant());
        dto.content = diary.getContent();
        dto.likes = diary.getLikes();
        dto.imagepath = diary.getImagePath();
        for(Tag t : diary.getTags()) {
            dto.taglist.add(TagResponseDTO.from(t));
        }
        for(Diary d : diarygroup) {
            dto.diarygroup.add(GroupedDiaryForSearchDTO.of(d));
        }
        return dto;
    }
}
