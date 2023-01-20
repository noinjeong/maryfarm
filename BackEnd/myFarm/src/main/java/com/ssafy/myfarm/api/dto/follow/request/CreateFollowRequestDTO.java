package com.ssafy.myfarm.api.dto.follow.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFollowRequestDTO {
    private String senderid;
    private String receiverid;
}
