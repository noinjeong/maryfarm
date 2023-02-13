package com.ssafy.maryfarmnotifyservice.client.dto.plant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDTO {
    private String diaryId;
    private String plantId;
    private String userId;
    private String name;
    private String title;
    private String content;
    private String imagepath;

}
