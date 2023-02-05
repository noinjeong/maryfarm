package com.ssafy.maryfarmconsumer.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow implements Serializable {
    /*
        Follow의 sender와 receiver 조합이 중복되는 경우는
        프론트엔드에서 팔로우 버튼을 누르면 언팔로우 버튼으로 바뀌는
        상황을 만들어 프론트엔드에서 해결해야 함.
     */
    private String id;
    private User senderUser;
    private User receiverUser;

    public static Follow of(User sender, User receiver) {
        Follow follow = new Follow();
        follow.senderUser = sender;
        follow.receiverUser = receiver;
        return follow;
    }
}
