package com.ssafy.myfarm.api.controller.diary;

import com.ssafy.myfarm.api.dto.diary.request.CreateDiaryRequestDTO;
import com.ssafy.myfarm.api.dto.diary.response.CreateDiaryResponseDTO;
import com.ssafy.myfarm.domain.FileInfo;
import com.ssafy.myfarm.domain.diary.Diary;
import com.ssafy.myfarm.domain.diary.DiaryImage;
import com.ssafy.myfarm.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class DiaryController {
    private final DiaryService diaryService;

    public ResponseEntity<?> saveDiary(@RequestPart MultipartFile image, @RequestPart CreateDiaryRequestDTO dto) throws IOException {
        /*
            saveDiary에 Diary 객체를 넘겨주는 게 아닌 Diary 객체를 만드는 데에 필요한 정보를 넘겨주는 게
            의존성 관리에 더 바람직함. Diary 객체를 넘겨주려면 Diary 객체에 대한 의존성이 생김.
         */
        Diary saveDiary = diaryService.saveDiary(dto.getTitle(), dto.getContent(), dto.getPlantId());
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
}
