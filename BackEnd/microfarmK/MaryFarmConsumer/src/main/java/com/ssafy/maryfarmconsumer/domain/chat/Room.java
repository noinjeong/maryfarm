package com.ssafy.maryfarmconsumer.domain.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
// 프록시 객체 생성만을 위한 생성자라 Protected로 사용제한
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room implements Serializable {
    private String id;
    private String senderId;
    private String receiverId;

    public static Room of(String senderId, String receiverId) {
        Room room = new Room();
        room.senderId = senderId;
        room.receiverId = receiverId;
        return room;
    }
}
