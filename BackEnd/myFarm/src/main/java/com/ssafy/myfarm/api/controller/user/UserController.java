package com.ssafy.myfarm.api.controller.user;

import com.ssafy.myfarm.api.dto.user.request.CreateUserRequestDTO;
import com.ssafy.myfarm.api.dto.user.request.LandRegistRequestDTO;
import com.ssafy.myfarm.api.dto.user.request.LoginRequestDTO;
import com.ssafy.myfarm.api.dto.user.request.ModifyUserRequestDTO;
import com.ssafy.myfarm.api.dto.user.response.UserResponseDTO;
import com.ssafy.myfarm.domain.user.Tier;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/user/signup")
    public ResponseEntity<?> saveUser(@RequestBody CreateUserRequestDTO dto) {
        User user = User.of(dto.getEmail(), dto.getPassword(), dto.getNickname(), dto.getBirthday(), "user", Tier.씨앗);
        User saveUser = userService.joinUser(user);
        UserResponseDTO resultDto = UserResponseDTO.from(saveUser);
        return ResponseEntity.ok(resultDto);
    }

    @PostMapping("/auth/user/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO dto) {
        User user = userService.loginUser(dto.getEmail(), dto.getPassword());
        UserResponseDTO resultDto = UserResponseDTO.from(user);
        return ResponseEntity.ok(resultDto);
    }

    @PutMapping("/user/modify")
    public ResponseEntity<?> modifyUser(@RequestBody ModifyUserRequestDTO dto) {
        userService.updateUser(dto.getId(),dto.getNickName());
        return ResponseEntity.ok(null);
    }

    @PostMapping("/user/land/regist")
    public ResponseEntity<?> registLand(@RequestBody LandRegistRequestDTO dto) {
        User user = userService.registLand(dto.getId(), dto.getLand());
        return ResponseEntity.ok(user);
    }
}
