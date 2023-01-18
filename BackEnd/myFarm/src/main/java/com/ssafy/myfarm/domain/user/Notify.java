package com.ssafy.myfarm.domain.user;

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
    @Enumerated(EnumType.STRING)
    private Type type;
    private String content;
    private boolean active;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public static Notify of(Type type, String content, boolean active, User user) {
        Notify notify = new Notify();
        notify.type = type;
        notify.content = content;
        notify.active = active;
        notify.user = user;
        return notify;
    }
}
