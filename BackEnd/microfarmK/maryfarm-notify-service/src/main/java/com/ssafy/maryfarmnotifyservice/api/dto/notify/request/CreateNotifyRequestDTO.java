package com.ssafy.maryfarmnotifyservice.api.dto.notify.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotifyRequestDTO {
    private String type;
    private String content;
    private String userId;
}
