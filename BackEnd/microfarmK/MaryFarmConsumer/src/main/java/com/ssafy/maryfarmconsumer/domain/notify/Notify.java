package com.ssafy.maryfarmconsumer.domain.notify;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notify implements Serializable {
    private String id;
    private String userId;
    private AlarmType type;
    private String content;
    private Boolean active;

    public static Notify of(AlarmType type, String content, boolean active, String userId) {
        Notify notify = new Notify();
        notify.type = type;
        notify.content = content;
        notify.active = active;
        notify.userId = userId;
        return notify;
    }
}
