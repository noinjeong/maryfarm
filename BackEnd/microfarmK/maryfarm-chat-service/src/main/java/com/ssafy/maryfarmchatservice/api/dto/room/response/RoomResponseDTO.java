package com.ssafy.maryfarmchatservice.api.dto.room.response;

import com.ssafy.maryfarmchatservice.domain.chat.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDTO {
    private String roomId;
    private String senderId;
    private String receiverId;

    public static RoomResponseDTO of(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.roomId = room.getId();
        dto.senderId = room.getSenderId();
        dto.receiverId = room.getReceiverId();
        return dto;
    }
}
