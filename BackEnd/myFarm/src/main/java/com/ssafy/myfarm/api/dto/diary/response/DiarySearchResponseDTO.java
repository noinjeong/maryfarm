package com.ssafy.myfarm.api.dto.diary.response;

import com.ssafy.myfarm.domain.diary.Diary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiarySearchResponseDTO {
    private Diary targetdiary;
    private List<Diary> groupDiary;

}
