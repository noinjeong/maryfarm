package com.ssafy.maryfarmplantservice.api.controller.diary;

import com.ssafy.maryfarmplantservice.api.dto.diary.request.*;
import com.ssafy.maryfarmplantservice.api.dto.diary.response.DetailDiaryResponseDTO;
import com.ssafy.maryfarmplantservice.api.dto.diary.response.DiarySearchResponseDTO;
import com.ssafy.maryfarmplantservice.api.dto.diary.response.DiaryToHomeResponseDTO;
import com.ssafy.maryfarmplantservice.api.dto.diary.response.FollowingDiaryResponseDTO;
import com.ssafy.maryfarmplantservice.api.dto.plant.response.PlantResponseDTO;
import com.ssafy.maryfarmplantservice.client.dto.user.UserResponseDTO;
import com.ssafy.maryfarmplantservice.client.service.user.UserServiceClient;
import com.ssafy.maryfarmplantservice.domain.diary.Diary;
import com.ssafy.maryfarmplantservice.domain.diary.DiaryComment;
import com.ssafy.maryfarmplantservice.domain.diary.DiaryLike;
import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import com.ssafy.maryfarmplantservice.kafka.dto.Status;
import com.ssafy.maryfarmplantservice.kafka.producer.diary.DiaryProducer;
import com.ssafy.maryfarmplantservice.kafka.producer.plant.PlantProducer;
import com.ssafy.maryfarmplantservice.service.DiaryService;
import com.ssafy.maryfarmplantservice.service.PlantService;
import com.ssafy.maryfarmplantservice.util.file.dto.FileDetail;
import com.ssafy.maryfarmplantservice.util.file.service.FileUploadService;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class DiaryController {
    private final DiaryService diaryService;
    private final PlantService plantService;
    private final FileUploadService fileUploadService;
    private final UserServiceClient userServiceClient;
    private final DiaryProducer diaryProducer;
    private final PlantProducer plantProducer;

    @Operation(summary = "일지 시작", description = "작물을 등록함과 동시에 일지를 시작합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
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
        Plant savePlant = plantService.savePlant(dto.getUserId(),dto.getTitle(),dto.getName());
        plantProducer.send("plant",savePlant, Status.C);
        FileDetail saveFile = fileUploadService.save(image);
        Diary saveDiary = diaryService.saveDiary(savePlant.getId(),dto.getContent(), saveFile.getPath());
        diaryProducer.send("diary",saveDiary,Status.C);
        return ResponseEntity.ok(saveDiary.getId());
    }

    @Operation(summary = "일지 추가", description = "일지를 추가로 등록합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/diary/add")
    public ResponseEntity<?> addDiary(@RequestPart MultipartFile image, @RequestPart AddDiaryRequestDTO dto) throws IOException {
        FileDetail saveFile = fileUploadService.save(image);
        Diary saveDiary = diaryService.saveDiary(dto.getPlantId(), dto.getContent(), saveFile.getPath());
        diaryProducer.send("diary",saveDiary,Status.C);
        return ResponseEntity.ok(saveDiary.getId());
    }

    @Operation(summary = "일지 수정", description = "일지를 수정합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
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
            updateDiary = diaryService.updateDiaryContentAndImage(dto.getDiaryId(), dto.getContent(), saveFile.getPath());
            diaryProducer.send("diary",updateDiary,Status.U);
        } else {
            updateDiary = diaryService.updateDiaryContent(dto.getDiaryId(), dto.getContent());
            diaryProducer.send("diary",updateDiary,Status.U);
        }
        return ResponseEntity.ok(updateDiary.getId());
    }

    @Operation(summary = "일지 좋아요 등록", description = "일지에 좋아요를 등록합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/diary/like")
    public ResponseEntity<?> giveDiaryLike(@RequestBody DiaryLikeRequestDTO dto) {
        DiaryLike diaryLike = diaryService.saveDiaryLike(dto.getDiaryId(),dto.getUserId());
        Diary diary = diaryService.addLike(dto.getDiaryId());
        diaryProducer.send("diary",diary,Status.U);
        return ResponseEntity.ok(diaryLike.getId());
    }

    @Operation(summary = "일지 댓글 등록", description = "일지에 댓글을 등록합니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/diary/comment")
    public ResponseEntity<?> giveDiaryComment(@RequestBody DiaryCommentRequestDTO dto) {
        DiaryComment diaryComment = diaryService.saveDiaryComment(dto.getDiaryId(),dto.getUserId(),dto.getContent());
        return ResponseEntity.ok(diaryComment.getId());
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
            UserResponseDTO userDto = userServiceClient.searchUser(d.getPlant().getUserId());
            resultDtos.add(DiarySearchResponseDTO.of(d,userDto,group));
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
    @GetMapping("/diary/follower/{userId}")
    public ResponseEntity<?> FollowerDiary(@PathVariable("userId") String userId) {
        List<UserResponseDTO> userDtos = userServiceClient.searchFollower(userId);
        List<FollowingDiaryResponseDTO> resultDtos = new ArrayList<>();
        for(UserResponseDTO u : userDtos) {
            List<Diary> list = diaryService.searchDiaryByUserId(u.getUserId());
            for(Diary d : list) {
                resultDtos.add(FollowingDiaryResponseDTO.of(d,u));
            }
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
    /*
        자신의 홈 화면에 작물 당 최신일지 들을 표시함.
     */
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
            UserResponseDTO userDto = userServiceClient.searchUser(d.getPlant().getUserId());
            resultDtos.add(DetailDiaryResponseDTO.of(d,userDto));
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "작물 일지 댓글 조회", description = "특정 작물 일지의 댓글들을 가져옵니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = DetailDiaryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/diary/comment/{diaryId}")
    public ResponseEntity<?> SearchDiaryComment(@PathVariable("diaryId") String diaryId) {
        List<DiaryComment> list = diaryService.searchDiaryComments(diaryId);
        List<DiaryCommentResponseDTO> resultDtos = new ArrayList<>();
        for(DiaryComment c : list) {
            resultDtos.add(DiaryCommentResponseDTO.of(c));
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "작물 일지 추천", description = "좋아요 순 상위 5개 작물 일지를 가져옵니다.", tags = { "Diary Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = DetailDiaryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/diary/top")
    public ResponseEntity<?> searchTopDiary() {
        List<Diary> list = diaryService.searchDiarysTopLike();
        List<DetailDiaryResponseDTO> resultDtos = new ArrayList<>();
        for(Diary d : list) {
            UserResponseDTO userDto = userServiceClient.searchUser(d.getPlant().getUserId());
            resultDtos.add(DetailDiaryResponseDTO.of(d,userDto));
        }
        return ResponseEntity.ok(resultDtos);
    }
}
