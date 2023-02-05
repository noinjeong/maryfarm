package com.ssafy.maryfarmchatservice.api.controller.room;

import com.ssafy.maryfarmchatservice.api.dto.room.request.InitRoomRequestDTO;
import com.ssafy.maryfarmchatservice.api.dto.room.response.RoomResponseDTO;
import com.ssafy.maryfarmchatservice.api.dto.room.response.SearchRoomResponseDTO;
import com.ssafy.maryfarmchatservice.client.dto.UserResponseDTO;
import com.ssafy.maryfarmchatservice.client.service.user.UserServiceClient;
import com.ssafy.maryfarmchatservice.domain.chat.Message;
import com.ssafy.maryfarmchatservice.domain.chat.Room;
import com.ssafy.maryfarmchatservice.kafka.dto.Status;
import com.ssafy.maryfarmchatservice.kafka.producer.room.RoomProducer;
import com.ssafy.maryfarmchatservice.service.MessageService;
import com.ssafy.maryfarmchatservice.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class RoomController {
    private final RoomService roomService;
    private final MessageService messageService;
    private final UserServiceClient userServiceClient;
    private final RoomProducer roomProducer;
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
        return ResponseEntity.ok(room.getId());
    }

    @Operation(summary = "채팅방 리스트", description = "자신의 채팅방들을 가져옵니다.", tags = { "Room Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SearchRoomResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/room/search/{userId}")
    public ResponseEntity<?> searchMyRoom(@PathVariable("userId") String userId) {
        List<Room> list = roomService.findByUser(userId);
        List<SearchRoomResponseDTO> resultDtos = new ArrayList<>();
        for(Room r : list) {
            Message latestMessage = messageService.searchLatestMessage(r.getId());
            if(latestMessage==null) {
                return ResponseEntity.noContent().build();
            }
            String opponentId = r.getReceiverId() == userId ? r.getReceiverId() : r.getSenderId();
            UserResponseDTO userResponseDTO = userServiceClient.searchUser(opponentId);
            resultDtos.add(new SearchRoomResponseDTO(userResponseDTO,latestMessage.getContent()));
        }
        return ResponseEntity.ok(resultDtos);
    }
}
