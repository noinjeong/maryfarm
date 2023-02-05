package com.ssafy.maryfarmchatservice.api.controller.message;

import com.ssafy.maryfarmchatservice.api.dto.message.request.MessageRequestDTO;
import com.ssafy.maryfarmchatservice.api.dto.message.response.MessageResponseDTO;
import com.ssafy.maryfarmchatservice.client.dto.UserResponseDTO;
import com.ssafy.maryfarmchatservice.client.service.user.UserServiceClient;
import com.ssafy.maryfarmchatservice.domain.chat.Message;
import com.ssafy.maryfarmchatservice.kafka.dto.Status;
import com.ssafy.maryfarmchatservice.kafka.producer.message.MessageProducer;
import com.ssafy.maryfarmchatservice.repository.MessageRepository;
import com.ssafy.maryfarmchatservice.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/api")
public class MessageController {
    private final MessageProducer messageProducer;
    private final MessageService messageService;
    private final UserServiceClient userServiceClient;
    private final SimpMessagingTemplate template;
    @Operation(summary = "채팅 메시지 저장", description = "채팅 메시지를 저장합니다.", tags = { "Chat Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/message/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequestDTO dto) throws ExecutionException, InterruptedException {
        Message saveMessage = messageService.saveMessage(dto.getRoomId(),dto.getUserId(),dto.getContent());
        System.out.println("sending via kafka listener..");
        System.out.println("/topic/group/" + saveMessage.getRoom().getId());
        template.convertAndSend("/topic/group/" + saveMessage.getRoom().getId(), saveMessage);
        return ResponseEntity.ok(saveMessage.getId());
    }

    @Operation(summary = "채팅 메시지 불러오기", description = "특정 방의 채팅 메시지를 불러옵니다.", tags = { "Chat Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/message/search/{roomId}")
    public ResponseEntity<?> searchMessage(@PathVariable("roomId") String roomId) throws ExecutionException, InterruptedException {
        List<Message> list = messageService.findMessageByRoom(roomId);
        List<MessageResponseDTO> resultDtos = new ArrayList<>();
        for(Message m : list) {
            UserResponseDTO userResponseDTO = userServiceClient.searchUser(m.getUserId());
            resultDtos.add(MessageResponseDTO.of(userResponseDTO,m));
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(hidden = true)
    //    -------------- WebSocket API ----------------
    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        //Sending this message to all the subscribers
        return message;
    }

    @Operation(hidden = true)
    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public Message addUser(@Payload Message message,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add user in web socket session
        headerAccessor.getSessionAttributes().put("username", message.getUserId());
        return message;
    }

}
