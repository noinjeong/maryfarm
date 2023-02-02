package com.ssafy.myfarm.api.controller.diary;

import com.ssafy.myfarm.api.dto.diary.request.*;
import com.ssafy.myfarm.api.dto.diary.response.DetailDiaryResponseDTO;
import com.ssafy.myfarm.api.dto.diary.response.DiarySearchResponseDTO;
import com.ssafy.myfarm.api.dto.diary.response.DiaryToHomeResponseDTO;
import com.ssafy.myfarm.api.dto.diary.response.FollowingDiaryResponseDTO;
import com.ssafy.myfarm.api.dto.user.response.UserResponseDTO;
import com.ssafy.myfarm.domain.diary.Diary;
import com.ssafy.myfarm.domain.diary.DiaryComment;
import com.ssafy.myfarm.domain.diary.DiaryLike;
import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.service.DiaryService;
import com.ssafy.myfarm.service.PlantService;
import com.ssafy.myfarm.util.file.dto.FileDetail;
import com.ssafy.myfarm.util.file.service.FileUploadService;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class DiaryController {
    private final DiaryService diaryService;
    private final PlantService plantService;
    private final FileUploadService fileUploadService;

    @Operation(summary = "일지 시작", description = "작물을 등록함과 동시에 일지를 시작합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = DetailDiaryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/diary/init")
    public ResponseEntity<?> initDiary(@RequestPart MultipartFile image, @RequestPart InitDiaryRequestDTO dto) throws IOException {
        /*
            saveDiary에 Diary 객체를 넘겨주는 게 아닌 Diary 객체를 만드는 데에 필요한 정보를 넘겨주는 게
            의존성 관리에 더 바람직함. Diary 객체를 넘겨주려면 Diary 객체에 대한 의존성이 생김.
         */
        Plant savePlant = plantService.savePlant(dto.getUserid(),dto.getTitle(),dto.getName());
        FileDetail saveFile = fileUploadService.save(image);
        Diary saveDiary = diaryService.saveDiary(savePlant.getId(),dto.getContent(), saveFile.getPath());
        return ResponseEntity.ok(DetailDiaryResponseDTO.of(saveDiary));
    }

    @Operation(summary = "일지 추가", description = "일지를 추가로 등록합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = DetailDiaryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/diary/add")
    public ResponseEntity<?> addDiary(@RequestPart MultipartFile image, @RequestPart AddDiaryRequestDTO dto) throws IOException {
        FileDetail saveFile = fileUploadService.save(image);
        Diary saveDiary = diaryService.saveDiary(dto.getPlantid(), dto.getContent(), saveFile.getPath());
        return ResponseEntity.ok(DetailDiaryResponseDTO.of(saveDiary));
    }

    @Operation(summary = "일지 수정", description = "일지를 수정합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = DetailDiaryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/diary/modify")
    public ResponseEntity<?> modifyDiary(@RequestPart MultipartFile image, @RequestPart ModifyDiaryRequestDTO dto) throws IOException {
        // 사진을 바꾸지 않는다면 image가 null값이 들어오므로 따로 처리안해줘도 됨.
        Diary updateDiary = null;
        if(!image.isEmpty()) {
            FileDetail saveFile = fileUploadService.save(image);
            updateDiary = diaryService.updateDiaryContentAndImage(dto.getDiaryid(), dto.getContent(), saveFile.getPath());
        } else {
            updateDiary = diaryService.updateDiaryContent(dto.getDiaryid(), dto.getContent());
        }
        return ResponseEntity.ok(DetailDiaryResponseDTO.of(updateDiary));
    }

    @Operation(summary = "일지 좋아요 등록", description = "일지에 좋아요를 등록합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/diary/like")
    public ResponseEntity<?> giveDiaryLike(@RequestBody DiaryLikeRequestDTO dto) {
        DiaryLike diaryLike = diaryService.saveDiaryLike(dto.getDiaryid(),dto.getUserid());
        diaryService.addLike(dto.getDiaryid());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "일지 댓글 등록", description = "일지에 댓글을 등록합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/diary/comment")
    public ResponseEntity<?> giveDiaryComment(@RequestBody DiaryCommentRequestDTO dto) {
        DiaryComment diaryComment = diaryService.saveDiaryComment(dto.getDiaryid(),dto.getUserid(),dto.getContent());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "일지 태그 검색", description = "일지 태그를 검색합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = DiarySearchResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/diary/tag/search")
    public ResponseEntity<?> searchPlant(@RequestBody SearchByTagRequestDTO dto) {
        List<Diary> list = diaryService.searchDiarysByTag(dto.getText());
        List<DiarySearchResponseDTO> resultDtos = new ArrayList<>();
        for(Diary d : list) {
            List<Diary> group = diaryService.searchDiaryGroup(d.getPlant().getId());
            resultDtos.add(DiarySearchResponseDTO.of(d,group));
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "팔로우 일지 조회", description = "팔로우한 사람들의 일지를 가져옵니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = FollowingDiaryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/diary/follower")
    public ResponseEntity<?> FollowerDiary(@RequestBody FollowerDiaryRequestDTO dto) {
        List<Diary> list = diaryService.findFollowerDiary(dto.getUserid());
        List<FollowingDiaryResponseDTO> resultDtos = new ArrayList<>();
        for(Diary d : list) {
            resultDtos.add(FollowingDiaryResponseDTO.of(d));
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "자신의 일지 조회", description = "자신의 일지들을 가져옵니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = DiaryToHomeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/diary/user/{userId}")
    public ResponseEntity<?> DiaryToHome(@PathVariable("userId") String userId) {
        List<Plant> list = plantService.searchPlantsByUserId(userId);
        List<DiaryToHomeResponseDTO> resultDtos = new ArrayList<>();
        for(Plant p : list) {
            Diary diary = diaryService.searchEarlyDiaryByPlant(p.getId());
            resultDtos.add(DiaryToHomeResponseDTO.of(diary));
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "작물 일지 조회", description = "특정 작물의 일지들을 가져옵니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = DetailDiaryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/diary/plant/{plantId}")
    public ResponseEntity<?> DetailDiary(@PathVariable("plantId") String plantId) {
        List<Diary> list = diaryService.searchDiarysByPlantId(plantId);
        List<DetailDiaryResponseDTO> resultDtos = new ArrayList<>();
        for(Diary d : list) {
            resultDtos.add(DetailDiaryResponseDTO.of(d));
        }
        return ResponseEntity.ok(resultDtos);
    }
}
