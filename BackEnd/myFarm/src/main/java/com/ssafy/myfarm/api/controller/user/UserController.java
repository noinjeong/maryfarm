package com.ssafy.myfarm.api.controller.user;

import com.ssafy.myfarm.api.dto.user.request.UserRecommendRequestDTO;
import com.ssafy.myfarm.api.dto.user.request.CreateUserRequestDTO;
import com.ssafy.myfarm.api.dto.user.request.LandRegistRequestDTO;
import com.ssafy.myfarm.api.dto.user.request.LoginRequestDTO;
import com.ssafy.myfarm.api.dto.user.request.ModifyUserRequestDTO;
import com.ssafy.myfarm.api.dto.user.response.UserResponseDTO;
import com.ssafy.myfarm.domain.user.Tier;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.service.UserService;
import com.ssafy.myfarm.util.file.dto.FileDetail;
import com.ssafy.myfarm.util.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final FileUploadService fileUploadService;

    @PostMapping("/auth/user/signup")
    public ResponseEntity<?> saveUser(@RequestBody CreateUserRequestDTO dto) {
        User user = User.of(dto.getKakaoid(), dto.getNickname(),Tier.씨앗);
        User saveUser = userService.saveUser(user);
        UserResponseDTO resultDto = UserResponseDTO.of(saveUser);
        return ResponseEntity.ok(resultDto);
    }

    @PostMapping("/auth/user/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO dto) {
        User user = userService.loginUser(dto.getKakaoid());
        if(user==null) return ResponseEntity.ok(0);
        else return ResponseEntity.ok(1);
    }

    @PutMapping("/user/modify")
    public ResponseEntity<?> modifyUser(@RequestPart MultipartFile image, @RequestPart ModifyUserRequestDTO dto) {
        FileDetail saveFile = fileUploadService.save(image);
        User user = userService.updateUser(dto.getUserid(), dto.getNickname(),saveFile.getPath());
        UserResponseDTO resultDto = UserResponseDTO.of(user);
        return ResponseEntity.ok(resultDto);
    }

    @PostMapping("/user/land/regist")
    public ResponseEntity<?> registLand(@RequestBody LandRegistRequestDTO dto) {
        User user = userService.registLand(dto.getUserid(), dto.getLatitude(),dto.getLongitude());
        UserResponseDTO resultDto = UserResponseDTO.of(user);
        return ResponseEntity.ok(resultDto);
    }
    @PostMapping("/user/recommend")
    public ResponseEntity<?> recommendPlant(@RequestBody UserRecommendRequestDTO dto) {
        String fullCode = dto.getMagnitude()+"/"+dto.getColor()+"/"+dto.getSeason()+"/"
                +dto.getPrice()+"/"+dto.getSize()+"/"+dto.getPeriod();
        userService.saveRecommend(dto.getUserid(),fullCode);
        return ResponseEntity.ok().build();
    }
}
