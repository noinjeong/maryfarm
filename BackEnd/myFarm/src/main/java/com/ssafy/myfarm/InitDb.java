package com.ssafy.myfarm;

import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.domain.user.Tier;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.PlantRepository;
import com.ssafy.myfarm.repository.UserRepository;
import com.ssafy.myfarm.service.DiaryService;
import com.ssafy.myfarm.service.PlantService;
import com.ssafy.myfarm.service.UserService;
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
        private final UserService userService;
        private final PlantService plantService;
        private final DiaryService diaryService;
        public void dbInit() {
            for(int i=1;i<=30;i++) {
                String si = Integer.toString(i);
                User user = User.of(si+si+si+si, "user"+si, Tier.씨앗);
                User saveUser = userService.saveUser(user);
                Plant plant = plantService.savePlant(saveUser.getId(), "방가방가" + si, "딸기");
                diaryService.saveDiary(plant.getId(),"#왕감자"+si+" 를 심었어요");
            }
        }
    }
}
