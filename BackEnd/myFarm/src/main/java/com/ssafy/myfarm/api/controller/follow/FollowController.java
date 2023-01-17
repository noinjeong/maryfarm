package com.ssafy.myfarm.api.controller.follow;

import com.ssafy.myfarm.api.dto.follow.CreateFollowRequestDTO;
import com.ssafy.myfarm.domain.user.Follow;
import com.ssafy.myfarm.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    public ResponseEntity<?> FollowUser(@RequestBody CreateFollowRequestDTO dto) {
        Follow follow = followService.saveFollow(dto.getSenderId(),dto.getReceiverId());

    }
}
