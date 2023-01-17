package com.ssafy.myfarm.api.controller.diary;

import com.ssafy.myfarm.api.dto.diary.request.*;
import com.ssafy.myfarm.api.dto.diary.response.CreateDiaryResponseDTO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class DiaryController {
    private final DiaryService diaryService;
    private final PlantService plantService;
    public ResponseEntity<?> initDiary(@RequestPart MultipartFile image, @RequestPart InitDiaryRequestDTO dto) throws IOException {
        /*
            saveDiary에 Diary 객체를 넘겨주는 게 아닌 Diary 객체를 만드는 데에 필요한 정보를 넘겨주는 게
            의존성 관리에 더 바람직함. Diary 객체를 넘겨주려면 Diary 객체에 대한 의존성이 생김.
         */
        Diary saveDiary = diaryService.saveDiary(dto.getTitle(), dto.getContent(), dto.getPlantId(), dto.getUserId());
        Plant savePlant = plantService.savePlant(dto.getUserId(),dto.getName());
        if(!image.isEmpty()) {
            String realPath = "C:/diary/upload/imageUpload";
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = realPath + File.separator + today;
            File folder = new File(saveFolder);
            if(!folder.exists()) folder.mkdirs();
            CreateDiaryResponseDTO response = new CreateDiaryResponseDTO();
            String originalFilename = image.getOriginalFilename();
            if(!originalFilename.isEmpty()) {
                String saveFileName = Long.toString(System.nanoTime())
                        + originalFilename.substring(originalFilename.lastIndexOf('.'));
                response.setFileInfo(new FileInfo(saveFolder,originalFilename,saveFileName));
                image.transferTo(new File(folder, saveFileName));
                DiaryImage diaryImage = diaryService.saveDiaryImage(saveDiary, saveFolder, originalFilename, saveFileName);
                return new ResponseEntity<CreateDiaryResponseDTO>(response, HttpStatus.OK);
            }
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    public ResponseEntity<?> addDiary(@RequestPart MultipartFile image, @RequestPart AddDiaryRequestDTO dto) throws IOException {
        Diary saveDiary = diaryService.saveDiary(dto.getTitle(), dto.getContent(), dto.getPlantId(), dto.getUserId());
        if(!image.isEmpty()) {
			String realPath = "C:/diary/upload/imageUpload";
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = realPath + File.separator + today;
			File folder = new File(saveFolder);
			if(!folder.exists()) folder.mkdirs();
            CreateDiaryResponseDTO response = new CreateDiaryResponseDTO();
            String originalFilename = image.getOriginalFilename();
            if(!originalFilename.isEmpty()) {
                String saveFileName = Long.toString(System.nanoTime())
						+ originalFilename.substring(originalFilename.lastIndexOf('.'));
                response.setFileInfo(new FileInfo(saveFolder,originalFilename,saveFileName));
                image.transferTo(new File(folder, saveFileName));
                DiaryImage diaryImage = diaryService.saveDiaryImage(saveDiary, saveFolder, originalFilename, saveFileName);
                return new ResponseEntity<CreateDiaryResponseDTO>(response, HttpStatus.OK);
            }
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    public ResponseEntity<?> giveDiaryLike(@RequestBody DiaryLikeRequestDTO dto) {
        DiaryLike diaryLike = diaryService.saveDiaryLike(dto.getDiaryId(),dto.getUserId());
        diaryService.addLike(dto.getDiaryId());
        return new ResponseEntity<DiaryLike>(diaryLike, HttpStatus.OK);
    }

    public ResponseEntity<?> giveDiaryComment(@RequestBody DiaryCommentRequestDTO dto) {
        DiaryComment diaryComment = diaryService.saveDiaryComment(dto.getDiaryId(),dto.getUserId(),dto.getContent());
        return new ResponseEntity<DiaryComment>(diaryComment,HttpStatus.OK);
    }
    public ResponseEntity<?> searchDiarys(@RequestBody SearchDiaryRequestDTO dto) {
        List<Diary> diaryList = diaryService.searchDiarysByPlant(dto.getPlantId());
        return new ResponseEntity<List<Diary>>(diaryList,HttpStatus.OK);
    }
}
