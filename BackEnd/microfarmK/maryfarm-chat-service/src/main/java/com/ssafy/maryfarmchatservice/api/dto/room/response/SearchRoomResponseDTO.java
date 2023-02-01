package com.ssafy.maryfarmchatservice.api.dto.room.response;

import com.ssafy.maryfarmchatservice.client.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRoomResponseDTO {
    private UserResponseDTO opponent;
    private String latestMessage;
}
