package com.ssafy.maryfarmnotifyservice.domain.notify;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notify {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "notify_id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Enumerated(EnumType.STRING)
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
