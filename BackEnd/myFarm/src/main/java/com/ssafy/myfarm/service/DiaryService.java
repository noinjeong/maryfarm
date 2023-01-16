package com.ssafy.myfarm.service;

import com.ssafy.myfarm.domain.FileInfo;
import com.ssafy.myfarm.domain.diary.Diary;
import com.ssafy.myfarm.domain.diary.DiaryImage;
import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.repository.DiaryImageRepository;
import com.ssafy.myfarm.repository.DiaryRepository;
import com.ssafy.myfarm.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final PlantRepository plantRepository;
    private final DiaryImageRepository diaryImageRepository;
    public Diary saveDiary(final String title, final String content, final Long plantId) {
        Optional<Plant> plant = plantRepository.findById(plantId);
        Diary diary = Diary.of(title, content, plant.get());
        /*
            save()의 매개변수로 들어가는 diary는 스스로 Id값이 갱신되는가?
         */
        Diary saveDiary = diaryRepository.save(diary);
        return saveDiary;
    }
    public List<Diary> findFollowerDiary(final Long userId, final Long lastPostId) {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id").descending());
        return diaryRepository.findFollowersDiary(userId, lastPostId, pageRequest);
    }

    public DiaryImage saveDiaryImage(Diary saveDiary, String saveFolder, String originalFilename, String saveFileName) {
        DiaryImage diaryImage = DiaryImage.of(saveDiary, new FileInfo(saveFolder, originalFilename, saveFileName));
        DiaryImage saveDiaryImage = diaryImageRepository.save(diaryImage);
        return saveDiaryImage;
    }
}
