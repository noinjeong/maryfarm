package com.ssafy.maryfarmconsumer.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommend implements Serializable {
    private String id;
    private User user;
    private String content;

    public static Recommend of(User user, String content) {
        Recommend recommend = new Recommend();
        recommend.user = user;
        recommend.content = content;
        return recommend;
    }
}
