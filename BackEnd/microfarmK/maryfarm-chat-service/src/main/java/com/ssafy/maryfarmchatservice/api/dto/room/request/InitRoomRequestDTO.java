package com.ssafy.maryfarmchatservice.api.dto.room.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitRoomRequestDTO {
    private String senderId;
    private String receiverId;
}
