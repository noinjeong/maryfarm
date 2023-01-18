package com.ssafy.myfarm;

import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.domain.user.Tier;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.PlantRepository;
import com.ssafy.myfarm.repository.UserRepository;
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
        public void dbInit() {
            for(int i=0;i<=30;i++) {
                String si = Integer.toString(i);
                User user = User.of("baek" + si + "@ssafy.com", "1234", "ssafy" + si, "199611" + si, "user", Tier.씨앗);
                User saveUser = userService.saveUser(user);
                plantService.savePlant(saveUser.getId(), "딸기" + si);
            }
        }
    }
}
