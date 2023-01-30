package com.ssafy.maryfarmcalendarservice.api.controller.calendar;

import com.ssafy.maryfarmcalendarservice.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class CalendarController {
    private final CalendarService calendarService;

    @Operation(summary = "특정 년월의 작물들 조회", description = "특정 년월에 해당하는 자신의 작물들을 조회합니다.", tags = { "Calendar Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PlantRes.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/user/modify")
    public ResponseEntity<?> modifyUser(@RequestPart MultipartFile image, @RequestPart ModifyUserRequestDTO dto) {
        FileDetail saveFile = fileUploadService.save(image);
        User user = userService.updateUser(dto.getUserId(), dto.getNickname(),saveFile.getPath());
        return ResponseEntity.ok(user.getId());
    }
}
