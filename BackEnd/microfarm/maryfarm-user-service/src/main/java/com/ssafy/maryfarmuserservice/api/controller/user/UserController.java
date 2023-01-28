package com.ssafy.maryfarmuserservice.api.controller.user;

import com.ssafy.maryfarmuserservice.api.dto.user.request.*;
import com.ssafy.maryfarmuserservice.api.dto.user.response.UserResponseDTO;
import com.ssafy.maryfarmuserservice.domain.user.Tier;
import com.ssafy.maryfarmuserservice.domain.user.User;
import com.ssafy.maryfarmuserservice.service.UserService;
import com.ssafy.maryfarmuserservice.util.file.dto.FileDetail;
import com.ssafy.maryfarmuserservice.util.file.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final FileUploadService fileUploadService;

    @Operation(summary = "회원 가입 요청", description = "회원 정보를 등록합니다.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/auth/user/signup")
    public ResponseEntity<?> saveUser(@RequestBody CreateUserRequestDTO dto) {
        User user = User.of(dto.getKakaoId(), dto.getNickname(), Tier.씨앗);
        User saveUser = userService.saveUser(user);
        UserResponseDTO resultDto = UserResponseDTO.of(saveUser);
        return ResponseEntity.ok(resultDto);
    }

    @Operation(summary = "로그인 요청", description = "회원 정보를 통해 로그인합니다.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/auth/user/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO dto) {
        User user = userService.loginUser(dto.getKakaoId());
        if(user==null) return ResponseEntity.ok(0);
        else return ResponseEntity.ok(1);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> searchUser(@PathVariable("userId") String userId) {
        User user = userService.findUser(userId);
        UserResponseDTO dto = UserResponseDTO.of(user);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/user/followers/{userId}")
    public ResponseEntity<?> searchFollower(@PathVariable("userId") String userId) {
        List<User> list = userService.searchFollowers(userId);
        List<UserResponseDTO> resultDtos = new ArrayList<>();
        for(User u : list) {
            resultDtos.add(UserResponseDTO.of(u));
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/user/modify")
    public ResponseEntity<?> modifyUser(@RequestPart MultipartFile image, @RequestPart ModifyUserRequestDTO dto) {
        FileDetail saveFile = fileUploadService.save(image);
        User user = userService.updateUser(dto.getUserId(), dto.getNickname(),saveFile.getPath());
        UserResponseDTO resultDto = UserResponseDTO.of(user);
        return ResponseEntity.ok(resultDto);
    }

    @Operation(summary = "실제 텃밭 등록", description = "실제 텃밭 주소를 등록합니다.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/user/land/regist")
    public ResponseEntity<?> registLand(@RequestBody LandRegistRequestDTO dto) {
        User user = userService.registLand(dto.getUserId(), dto.getLatitude(),dto.getLongitude());
        UserResponseDTO resultDto = UserResponseDTO.of(user);
        return ResponseEntity.ok(resultDto);
    }

    @Operation(summary = "추천용 응답 저장", description = "작물 추천을 받기 위한 응답을 저장합니다.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/user/recommend")
    public ResponseEntity<?> recommendPlant(@RequestBody UserRecommendRequestDTO dto) {
        String fullCode = dto.getMagnitude()+"/"+dto.getColor()+"/"+dto.getSeason()+"/"
                +dto.getPrice()+"/"+dto.getSize()+"/"+dto.getPeriod();
        userService.saveRecommend(dto.getUserId(),fullCode);
        return ResponseEntity.ok().build();
    }
}
