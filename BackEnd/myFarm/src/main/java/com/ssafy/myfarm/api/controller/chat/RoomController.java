package com.ssafy.myfarm.api.controller.chat;

import com.ssafy.myfarm.api.dto.chat.request.InitRoomRequestDTO;
import com.ssafy.myfarm.api.dto.chat.response.RoomResponseDTO;
import com.ssafy.myfarm.domain.chat.Room;
import com.ssafy.myfarm.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/room/init")
    public ResponseEntity<?> saveRoom(@RequestBody InitRoomRequestDTO dto) {
        Room room = roomService.saveRoom(dto.getSenderid(), dto.getReceiverid());
        return ResponseEntity.ok(RoomResponseDTO.of(room));
    }
}
