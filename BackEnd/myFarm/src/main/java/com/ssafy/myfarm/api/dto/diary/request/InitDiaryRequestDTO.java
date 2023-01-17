package com.ssafy.myfarm.api.dto.diary.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitDiaryRequestDTO {
    private Long plantId;
    private Long userId;
    private String name;
    private String title;
    private String content;
}
