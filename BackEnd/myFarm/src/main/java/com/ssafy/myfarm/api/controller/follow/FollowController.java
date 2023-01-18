package com.ssafy.myfarm.api.controller.follow;

import com.ssafy.myfarm.api.dto.follow.request.CreateFollowRequestDTO;
import com.ssafy.myfarm.api.dto.follow.response.FollowResponseDTO;
import com.ssafy.myfarm.domain.user.Follow;
import com.ssafy.myfarm.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/follow/following")
    public ResponseEntity<?> FollowUser(@RequestBody CreateFollowRequestDTO dto) {
        Follow follow = followService.saveFollow(dto.getSenderid(),dto.getReceiverid());
        FollowResponseDTO resultDto = FollowResponseDTO.of(follow);
        return ResponseEntity.ok(resultDto);
    }
}
