package com.ssafy.myfarm.api.controller.diary;

import com.ssafy.myfarm.api.dto.diary.request.*;
import com.ssafy.myfarm.api.dto.diary.response.DiaryResponseDTO;
import com.ssafy.myfarm.domain.FileInfo;
import com.ssafy.myfarm.domain.diary.Diary;
import com.ssafy.myfarm.domain.diary.DiaryComment;
import com.ssafy.myfarm.domain.diary.DiaryImage;
import com.ssafy.myfarm.domain.diary.DiaryLike;
import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.service.DiaryService;
import com.ssafy.myfarm.service.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        Diary saveDiary = diaryService.saveDiary(dto.getTitle(), dto.getContent(), dto.getPlantid(), dto.getUserid());
        Plant savePlant = plantService.savePlant(dto.getUserid(),dto.getName());
        if(!image.isEmpty()) {
            String realPath = "C:/diary/upload/imageUpload";
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = realPath + File.separator + today;
            File folder = new File(saveFolder);
            if(!folder.exists()) folder.mkdirs();
            DiaryResponseDTO resultDto = DiaryResponseDTO.of(saveDiary);
            String originalFilename = image.getOriginalFilename();
            if(!originalFilename.isEmpty()) {
                String saveFileName = Long.toString(System.nanoTime())
                        + originalFilename.substring(originalFilename.lastIndexOf('.'));
                image.transferTo(new File(folder, saveFileName));
                DiaryImage diaryImage = diaryService.saveDiaryImage(saveDiary, saveFolder, originalFilename, saveFileName);
                return ResponseEntity.ok(resultDto);
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/diary/add")
    public ResponseEntity<?> addDiary(@RequestPart MultipartFile image, @RequestPart AddDiaryRequestDTO dto) throws IOException {
        Diary saveDiary = diaryService.saveDiary(dto.getTitle(), dto.getContent(), dto.getPlantid(), dto.getUserid());
        if(!image.isEmpty()) {
			String realPath = "C:/diary/upload/imageUpload";
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = realPath + File.separator + today;
			File folder = new File(saveFolder);
			if(!folder.exists()) folder.mkdirs();
            DiaryResponseDTO resultDto = DiaryResponseDTO.of(saveDiary);
            String originalFilename = image.getOriginalFilename();
            if(!originalFilename.isEmpty()) {
                String saveFileName = Long.toString(System.nanoTime())
						+ originalFilename.substring(originalFilename.lastIndexOf('.'));
                image.transferTo(new File(folder, saveFileName));
                DiaryImage diaryImage = diaryService.saveDiaryImage(saveDiary, saveFolder, originalFilename, saveFileName);
                return ResponseEntity.ok(resultDto);
            }
			return ResponseEntity.badRequest().build();
		}
        return ResponseEntity.ok().build();
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
    @PostMapping("/diary/search")
    public ResponseEntity<?> searchDiarys(@RequestBody SearchDiaryRequestDTO dto) {
        List<Diary> list = diaryService.searchDiarysByPlant(dto.getPlantid());
        List<DiaryResponseDTO> resultDtos = new ArrayList<>();
        for(Diary d : list) {
            resultDtos.add(DiaryResponseDTO.of(d));
        }
        return ResponseEntity.ok(resultDtos);
    }
    @PostMapping("/diary/follower")
    public ResponseEntity<?> FollowerDiary(@RequestBody FollowerDiaryRequestDTO dto) {
        List<Diary> list = diaryService.findFollowerDiary(dto.getUserid());
        List<DiaryResponseDTO> resultDtos = new ArrayList<>();
        for(Diary d : list) {
            resultDtos.add(DiaryResponseDTO.of(d));
        }
        return ResponseEntity.ok(resultDtos);
    }
}
