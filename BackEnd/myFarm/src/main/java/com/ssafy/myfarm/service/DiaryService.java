package com.ssafy.myfarm.service;

import com.ssafy.myfarm.domain.FileInfo;
import com.ssafy.myfarm.domain.diary.Diary;
import com.ssafy.myfarm.domain.diary.DiaryComment;
import com.ssafy.myfarm.domain.diary.DiaryImage;
import com.ssafy.myfarm.domain.diary.DiaryLike;
import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.domain.tag.Tag;
import com.ssafy.myfarm.domain.user.Notify;
import com.ssafy.myfarm.domain.user.Type;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryCommentRepository diaryCommentRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final NotifyRepository notifyRepository;
    @Transactional
    public Diary saveDiary(final String title, final String content, final Long plantId, final Long userId) {
        Optional<Plant> plant = plantRepository.findById(plantId);
        Diary diary = Diary.of(title, content, plant.get());
        /*
            save()의 매개변수로 들어가는 diary는 스스로 Id값이 갱신되는가?
         */
        Diary saveDiary = diaryRepository.save(diary);
        // 알람 생성 시작
        Optional<User> user = userRepository.findById(userId);
        String notifyContent = user.get().getNickname()+"님이 새로운 일지를 올렸어요!";
        List<User> follower = userRepository.findFollower(userId);
        for(User u:follower) {
            Notify notify = Notify.of(Type.FollowerUpload, notifyContent, true, u);
            notifyRepository.save(notify);
        }
        // 알람 생성 종료
        // 태그 파싱 및 등록 시작
        List<String> tagList = HashTagParsing(content);
        for(String s : tagList) {
            Tag tag = Tag.of(plant.get(), s);
            tagRepository.save(tag);
        }
        // 태그 파싱 및 등록 종료
        return saveDiary;
    }

//    public List<Diary> findFollowerDiary(final Long userId, final Long lastPostId) {
//        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id").descending());
//        return diaryRepository.findFollowersDiary(userId, lastPostId, pageRequest);
//    }

    public List<Diary> findFollowerDiary(final Long userId) {
        return diaryRepository.findFollowersDiary(userId);
    }

    @Transactional
    public DiaryImage saveDiaryImage(Diary saveDiary, String saveFolder, String originalFilename, String saveFileName) {
        DiaryImage diaryImage = DiaryImage.of(saveDiary, new FileInfo(saveFolder, originalFilename, saveFileName));
        DiaryImage saveDiaryImage = diaryImageRepository.save(diaryImage);
        return saveDiaryImage;
    }

    @Transactional
    public DiaryLike saveDiaryLike(Long diaryId, Long userId) {
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        Optional<User> user = userRepository.findById(userId);
        DiaryLike diaryLike = DiaryLike.of(diary.get(), user.get());
        DiaryLike saveDiaryLike = diaryLikeRepository.save(diaryLike);
        return saveDiaryLike;
    }

    @Transactional
    public void addLike(Long diaryId) {
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        diary.get().addLike();
    }

    @Transactional
    public DiaryComment saveDiaryComment(Long diaryId, Long userId, String content) {
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        Optional<User> user = userRepository.findById(userId);
        DiaryComment diaryComment = DiaryComment.of(diary.get(), user.get(), content);
        DiaryComment saveDiaryComment = diaryCommentRepository.save(diaryComment);
        return saveDiaryComment;
    }

    private List<String> HashTagParsing(String content) {
        String[] tags = content.split("#([A-Za-z0-9_ㄱ-ㅎㅏ-ㅣ가-힣](?:(?:[A-Za-z0-9_ㄱ-ㅎㅏ-ㅣ가-힣]|(?:\\.(?!\\.))){0,28}(?:[A-Za-z0-9_ㄱ-ㅎㅏ-ㅣ가-힣]))?)(\\s)");
        List<String> result = new ArrayList<>();
        for(String s: tags) {
            result.add(s.substring(1,s.length()-1));
        }
        return result;
    }

    public List<Diary> searchDiarysByTag(String text) {
        return diaryRepository.findDiarysByTag(text);
    }

    public List<Diary> searchDiaryGroup(String plantId) {
        return diaryRepository.findDiaryGroup(plantId);
    }
}
