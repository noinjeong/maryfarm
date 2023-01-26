package com.ssafy.myfarm.service;

import com.ssafy.myfarm.domain.user.Follow;
import com.ssafy.myfarm.domain.user.Notify;
import com.ssafy.myfarm.domain.user.AlarmType;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.FollowRepository;
import com.ssafy.myfarm.repository.NotifyRepository;
import com.ssafy.myfarm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final NotifyRepository notifyRepository;

    @Transactional
    public Follow saveFollow(String senderId, String receiverId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);
        Follow follow = Follow.of(sender.get(), receiver.get());
        Follow saveFollow = followRepository.save(follow);

        // 알람 생성 시작
        String content = sender.get().getNickname() + "님이 내 농장 이웃이 되었어요!";
        Notify notify = Notify.of(AlarmType.FollowRequest, content, true, receiver.get());
        Notify saveNotify = notifyRepository.save(notify);
        // 알람 생성 끝
        return saveFollow;
    }
}
