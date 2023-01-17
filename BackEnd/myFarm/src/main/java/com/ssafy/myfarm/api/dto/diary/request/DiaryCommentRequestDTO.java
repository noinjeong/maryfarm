package com.ssafy.myfarm.api.dto.diary.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryCommentRequestDTO {
    private Long diaryId;
    private Long userId;
    private String content;
}
