package com.ssafy.maryfarmchatservice.domain.chat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.maryfarmchatservice.api.dto.message.request.MessageRequestDTO;
import com.ssafy.maryfarmchatservice.formatter.LocalDateTimeDeserializer;
import com.ssafy.maryfarmchatservice.formatter.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message implements Serializable {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "message_id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "user_id")
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
