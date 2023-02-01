package com.ssafy.maryfarmchatservice.api.dto.message.response;

import com.ssafy.maryfarmchatservice.client.dto.UserResponseDTO;
import com.ssafy.maryfarmchatservice.domain.chat.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {
    private UserResponseDTO user;
    private String messageId;
    private String content;
    private LocalDateTime timestamp;

    public static MessageResponseDTO of(UserResponseDTO user, Message message) {
        MessageResponseDTO dto = new MessageResponseDTO();
        dto.user = user;
        dto.messageId = message.getId();
        dto.content = message.getContent();
        dto.timestamp = message.getTimestamp();
        return dto;
    }
}
