package com.ssafy.maryfarmconsumer.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBadge implements Serializable {
    private String id;
    private User user;
    private Badge badge;
}
