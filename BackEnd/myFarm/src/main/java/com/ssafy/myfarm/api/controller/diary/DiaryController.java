package com.ssafy.myfarm.api.controller.diary;

import com.ssafy.myfarm.api.dto.diary.request.*;
import com.ssafy.myfarm.api.dto.diary.response.DetailDiaryResponseDTO;
import com.ssafy.myfarm.api.dto.diary.response.DiarySearchResponseDTO;
import com.ssafy.myfarm.api.dto.diary.response.DiaryToHomeResponseDTO;
import com.ssafy.myfarm.api.dto.diary.response.FollowingDiaryResponseDTO;
import com.ssafy.myfarm.domain.diary.Diary;
import com.ssafy.myfarm.domain.diary.DiaryComment;
import com.ssafy.myfarm.domain.diary.DiaryLike;
import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.service.DiaryService;
import com.ssafy.myfarm.service.PlantService;
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
    @PostMapping("/diary/init")
    public ResponseEntity<?> initDiary(@RequestPart MultipartFile image, @RequestPart InitDiaryRequestDTO dto) throws IOException {
        /*
            saveDiary에 Diary 객체를 넘겨주는 게 아닌 Diary 객체를 만드는 데에 필요한 정보를 넘겨주는 게
            의존성 관리에 더 바람직함. Diary 객체를 넘겨주려면 Diary 객체에 대한 의존성이 생김.
         */
        Plant savePlant = plantService.savePlant(dto.getUserid(),dto.getTitle(),dto.getName());
        Diary saveDiary = diaryService.saveDiary(savePlant.getId(),dto.getContent());
        return ResponseEntity.ok(saveDiary.getId());
    }
    @PostMapping("/diary/add")
    public ResponseEntity<?> addDiary(@RequestPart MultipartFile image, @RequestPart AddDiaryRequestDTO dto) throws IOException {
        Diary saveDiary = diaryService.saveDiary(dto.getPlantid(), dto.getContent());
        return ResponseEntity.ok(saveDiary.getId());
    }
    @PostMapping("/diary/like")
    public ResponseEntity<?> giveDiaryLike(@RequestBody DiaryLikeRequestDTO dto) {
        DiaryLike diaryLike = diaryService.saveDiaryLike(dto.getDiaryid(),dto.getUserid());
        diaryService.addLike(dto.getDiaryid());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/diary/comment")
    public ResponseEntity<?> giveDiaryComment(@RequestBody DiaryCommentRequestDTO dto) {
        DiaryComment diaryComment = diaryService.saveDiaryComment(dto.getDiaryid(),dto.getUserid(),dto.getContent());
        return ResponseEntity.ok().build();
    }
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
    @PostMapping("/diary/follower")
    public ResponseEntity<?> FollowerDiary(@RequestBody FollowerDiaryRequestDTO dto) {
        List<Diary> list = diaryService.findFollowerDiary(dto.getUserid());
        List<FollowingDiaryResponseDTO> resultDtos = new ArrayList<>();
        for(Diary d : list) {
            resultDtos.add(FollowingDiaryResponseDTO.of(d));
        }
        return ResponseEntity.ok(resultDtos);
    }

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
