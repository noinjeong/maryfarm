package com.ssafy.myfarm.api.dto.diary.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddDiaryRequestDTO {
    private Long plantId;
    private Long userId;
    private String title;
    private String content;
}
