package com.ssafy.myfarm.api.dto.diary.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitDiaryRequestDTO {
    private Long plantid;
    private Long userid;
    private String name;
    private String title;
    private String content;
}
