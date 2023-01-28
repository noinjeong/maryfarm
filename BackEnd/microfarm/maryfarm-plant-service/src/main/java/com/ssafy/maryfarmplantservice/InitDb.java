package com.ssafy.maryfarmplantservice;

import com.ssafy.maryfarmplantservice.domain.diary.Diary;
import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import com.ssafy.maryfarmplantservice.service.DiaryService;
import com.ssafy.maryfarmplantservice.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final PlantService plantService;
        private final DiaryService diaryService;

        public void dbInit() {
//            User user = User.of("123456", "baek", Tier.씨앗);
//            User saveUser1 = userService.saveUser(user);
//            User user2 = User.of("1234567", "seung", Tier.씨앗);
//            User saveUser2 = userService.saveUser(user2);
//            followService.saveFollow(saveUser1.getId(),saveUser2.getId());
            Plant plant1 = Plant.of("123456", "왕감자 일지 시작!", "감자");
            Plant savePlant1 = plantService.savePlant(plant1.getUserId(), plant1.getTitle(), plant1.getName());
            Plant plant2 = Plant.of("1234567", "왕딸기 일지 시작!", "딸기");
            Plant savePlant2 = plantService.savePlant(plant2.getUserId(), plant2.getTitle(), plant2.getName());
            Diary diary1 = Diary.of(plant1, "#왕감자 심기 시작함!", "010101");
            Diary diary2 = Diary.of(plant2, "#왕딸기 심기 시작함!", "01010123");
            diaryService.saveDiary(savePlant1.getId(),diary1.getContent(),diary1.getImagePath());
            diaryService.saveDiary(savePlant2.getId(),diary2.getContent(),diary2.getImagePath());
        }
    }
}
