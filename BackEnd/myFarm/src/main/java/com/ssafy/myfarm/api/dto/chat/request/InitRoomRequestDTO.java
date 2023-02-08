package com.ssafy.myfarm.api.dto.chat.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitRoomRequestDTO {
    private String senderid;
    private String receiverid;
}
