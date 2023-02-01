package com.ssafy.maryfarmplantservice.service;

import com.ssafy.maryfarmplantservice.client.dto.notify.CreateNotifyRequestDTO;
import com.ssafy.maryfarmplantservice.client.dto.user.UserResponseDTO;
import com.ssafy.maryfarmplantservice.client.service.notify.NotifyServiceClient;
import com.ssafy.maryfarmplantservice.client.service.user.UserServiceClient;
import com.ssafy.maryfarmplantservice.domain.diary.Diary;
import com.ssafy.maryfarmplantservice.domain.diary.DiaryComment;
import com.ssafy.maryfarmplantservice.domain.diary.DiaryLike;
import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import com.ssafy.maryfarmplantservice.domain.tag.Tag;
import com.ssafy.maryfarmplantservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final PlantRepository plantRepository;
    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryCommentRepository diaryCommentRepository;
    private final TagRepository tagRepository;
    private final UserServiceClient userServiceClient;
    private final NotifyServiceClient notifyServiceClient;
    @Transactional
    public Diary saveDiary(final String plantId, final String content, final String imagePath) {
        Optional<Plant> plant = plantRepository.findById(plantId);
        Diary diary = Diary.of(plant.get(),content,imagePath);
        /*
            save()의 매개변수로 들어가는 diary는 스스로 Id값이 갱신되는가?
         */
        Diary saveDiary = diaryRepository.save(diary);
//        // 알람 생성 시작
//        String userId = plant.get().getUserId();
//        UserResponseDTO userDto = userServiceClient.searchUser(userId);
//        String notifyContent = userDto.getNickname()+"님이 새로운 일지를 올렸어요!";
//        List<UserResponseDTO> followerDto = userServiceClient.searchFollower(userId);
//        for(UserResponseDTO u : followerDto) {
//            CreateNotifyRequestDTO notifyDto = new CreateNotifyRequestDTO("FollowerUpload", notifyContent, u.getUserId());
//            notifyServiceClient.saveNotify(notifyDto);
//        }
//        // 알람 생성 종료
        // 태그 파싱 및 등록 시작
        List<String> tagList = HashTagParsing(content);
        for(String s : tagList) {
            Tag tag = Tag.of(saveDiary, s);
            tagRepository.save(tag);
        }
        // 태그 파싱 및 등록 종료
        return saveDiary;
    }

//    public List<Diary> findFollowerDiary(final Long userId, final Long lastPostId) {
//        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id").descending());
//        return diaryRepository.findFollowersDiary(userId, lastPostId, pageRequest);
//    }
    @Transactional
    public Diary updateDiaryContent(String diaryid, String content) {
        Optional<Diary> diary = diaryRepository.findById(diaryid);
        diary.get().setContent(content);
        return diary.get();
    }

    @Transactional
    public Diary updateDiaryContentAndImage(String diaryid, String content, String path) {
        Optional<Diary> diary = diaryRepository.findById(diaryid);
        diary.get().setContent(content);
        diary.get().setImagePath(path);
        return diary.get();
    }

    @Transactional
    public DiaryLike saveDiaryLike(String diaryId, String userId) {
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        DiaryLike diaryLike = DiaryLike.of(userId, diary.get());
        DiaryLike saveDiaryLike = diaryLikeRepository.save(diaryLike);
        return saveDiaryLike;
    }

    @Transactional
    public Diary addLike(String diaryId) {
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        diary.get().addLike();
        return diary.get();
    }

    @Transactional
    public DiaryComment saveDiaryComment(String diaryId, String userId, String content) {
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        DiaryComment diaryComment = DiaryComment.of(diary.get(), userId, content);
        DiaryComment saveDiaryComment = diaryCommentRepository.save(diaryComment);
        return saveDiaryComment;
    }

    private List<String> HashTagParsing(String content) {
        List<String> result = new ArrayList<>();
        Pattern p = Pattern.compile("#([A-Za-z0-9_ㄱ-ㅎㅏ-ㅣ가-힣](?:(?:[A-Za-z0-9_ㄱ-ㅎㅏ-ㅣ가-힣]|(?:\\.(?!\\.))){0,28}(?:[A-Za-z0-9_ㄱ-ㅎㅏ-ㅣ가-힣]))?)(\\s)");
        Matcher m = p.matcher(content);
        while(m.find()) {
            String s = m.group();
            result.add(s.substring(1,s.length()-1));
        }
        return result;
    }

    public List<Diary> searchDiarysByTag(String text) {
        return diaryRepository.findDiaryByTag(text);
    }

    public List<Diary> searchDiaryGroup(String plantId) {
        return diaryRepository.findDiaryGroup(plantId);
    }

    public List<Diary> searchDiaryByUserId(String userId) {
        return diaryRepository.findDiaryByUserId(userId);
    }

    public Diary searchEarlyDiaryByPlant(String plantId) {
        return diaryRepository.findEarlyDiaryByPlantId(plantId, PageRequest.of(0,1)).get(0);
    }

    public List<Diary> searchDiarysByPlantId(String plantId) {
        return diaryRepository.findDiaryByPlantId(plantId);
    }

    public List<Diary> searchDiarysTopLike() {
        return diaryRepository.findTop5ByOrderByLikesDesc();
    }

    public List<DiaryComment> searchDiaryComments(String diaryId) {
        return diaryCommentRepository.findByDiary_Id(diaryId);
    }
}
