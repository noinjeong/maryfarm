package com.ssafy.myfarm.api.dto.chat.response;

import com.ssafy.myfarm.domain.chat.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDTO {
    private String roomid;
    private String senderid;
    private String receiverid;

    public static RoomResponseDTO of(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.roomid = room.getId();
        dto.senderid = room.getUser1().getId();
        dto.receiverid = room.getUser2().getId();
        return dto;
    }
}
