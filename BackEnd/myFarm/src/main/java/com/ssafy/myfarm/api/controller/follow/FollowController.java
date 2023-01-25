package com.ssafy.myfarm.api.controller.follow;

import com.ssafy.myfarm.api.dto.follow.request.CreateFollowRequestDTO;
import com.ssafy.myfarm.api.dto.follow.response.FollowResponseDTO;
import com.ssafy.myfarm.api.dto.user.response.UserResponseDTO;
import com.ssafy.myfarm.domain.user.Follow;
import com.ssafy.myfarm.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "팔로우 요청", description = "다른 유저를 팔로우합니다.", tags = { "Follow Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = FollowResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/follow/following")
    public ResponseEntity<?> FollowUser(@RequestBody CreateFollowRequestDTO dto) {
        Follow follow = followService.saveFollow(dto.getSenderid(),dto.getReceiverid());
        FollowResponseDTO resultDto = FollowResponseDTO.of(follow);
        return ResponseEntity.ok(resultDto);
    }
}
