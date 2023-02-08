package com.ssafy.maryfarmchatservice.api.controller.chat;

import com.ssafy.maryfarmchatservice.api.dto.chat.request.InitRoomRequestDTO;
import com.ssafy.maryfarmchatservice.api.dto.chat.response.RoomResponseDTO;
import com.ssafy.maryfarmchatservice.domain.chat.Room;
import com.ssafy.maryfarmchatservice.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "채팅방 등록", description = "채팅방을 등록합니다.", tags = { "Room Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = RoomResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/room/init")
    public ResponseEntity<?> saveRoom(@RequestBody InitRoomRequestDTO dto) {
        Room room = roomService.saveRoom(dto.getSenderId(), dto.getReceiverId());
        return ResponseEntity.ok(RoomResponseDTO.of(room));
    }
}
