package com.ssafy.maryfarmchatservice.domain.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
// 프록시 객체 생성만을 위한 생성자라 Protected로 사용제한
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room implements Serializable {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "room_id")
    private String id;
    @Column(name = "sender_id")
    private String senderId;
    @Column(name = "receiver_id")
    private String receiverId;

    public static Room of(String senderId, String receiverId) {
        Room room = new Room();
        room.senderId = senderId;
        room.receiverId = receiverId;
        return room;
    }
}
