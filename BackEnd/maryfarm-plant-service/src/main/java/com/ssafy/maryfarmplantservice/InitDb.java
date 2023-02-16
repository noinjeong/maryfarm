package com.ssafy.maryfarmplantservice;

import com.ssafy.maryfarmplantservice.domain.diary.Diary;
import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import com.ssafy.maryfarmplantservice.jpa_repository.DiaryRepository;
import com.ssafy.maryfarmplantservice.jpa_repository.PlantRepository;
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
        private final PlantRepository plantRepository;
        private final DiaryRepository diaryRepository;

        public void dbInit() {
//            User user = User.of("123456", "baek", Tier.씨앗);
//            User saveUser1 = userService.saveUser(user);
//            User user2 = User.of("1234567", "seung", Tier.씨앗);
//            User saveUser2 = userService.saveUser(user2);
//            followService.saveFollow(saveUser1.getId(),saveUser2.getId());
            for(int i=0;i<100;i++) {
                Plant plant1 = Plant.of("user"+i, "왕감자 일지 시작!", "감자");
                Plant savePlant1 = plantRepository.save(plant1);
                Diary diary1 = Diary.of(savePlant1,"안녕","1111","user"+i,"baek"+i,"1212","왕감자심기",plant1.getCreatedDate(),null);
                diaryRepository.save(diary1);

            }
        }
    }
}
