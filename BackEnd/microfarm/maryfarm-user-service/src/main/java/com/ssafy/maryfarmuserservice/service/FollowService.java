package com.ssafy.maryfarmuserservice.service;

import com.ssafy.maryfarmuserservice.client.dto.notify.CreateNotifyRequestDTO;
import com.ssafy.maryfarmuserservice.client.service.notify.NotifyServiceClient;
import com.ssafy.maryfarmuserservice.domain.user.Follow;
import com.ssafy.maryfarmuserservice.domain.user.User;
import com.ssafy.maryfarmuserservice.repository.FollowRepository;
import com.ssafy.maryfarmuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
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
    private final NotifyServiceClient notifyServiceClient;

    @Transactional
    public Follow saveFollow(String senderId, String receiverId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);
        Follow follow = Follow.of(sender.get(), receiver.get());
        Follow saveFollow = followRepository.save(follow);
        // 알람 생성 시작
        String content = follow.getSenderUser().getNickname() + "님이 내 농장 이웃이 되었어요!";
        CreateNotifyRequestDTO requestDTO = new CreateNotifyRequestDTO("FollowRequest", content, receiverId);
        notifyServiceClient.saveNotify(requestDTO);
        // 알람 생성 끝
        return saveFollow;
    }
}
