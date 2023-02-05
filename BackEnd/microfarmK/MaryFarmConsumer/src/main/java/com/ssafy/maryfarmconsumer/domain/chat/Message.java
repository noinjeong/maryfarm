package com.ssafy.maryfarmconsumer.domain.chat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.maryfarmconsumer.formatter.LocalDateTimeDeserializer;
import com.ssafy.maryfarmconsumer.formatter.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message implements Serializable {
    private String id;
    private Room room;
    private String userId;
    private String content;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    public static Message of(Room room, String userId, String content, LocalDateTime timestamp) {
        Message message = new Message();
        message.room = room;
        message.userId = userId;
        message.content = content;
        message.timestamp = timestamp;
        return message;
    }
}
