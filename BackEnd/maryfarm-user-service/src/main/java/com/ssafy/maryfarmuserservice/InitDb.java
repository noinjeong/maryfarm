package com.ssafy.maryfarmuserservice;

import com.ssafy.maryfarmuserservice.domain.user.Tier;
import com.ssafy.maryfarmuserservice.domain.user.User;
import com.ssafy.maryfarmuserservice.service.FollowCService;
import com.ssafy.maryfarmuserservice.service.UserCService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() throws InterruptedException {
//        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final UserCService userCService;
        private final FollowCService followCService;
        public void dbInit() throws InterruptedException {
//            User user2 = User.of("15571557", "seung", Tier.씨앗);
//            User saveUser2 = userCService.saveUser(user2);
//            Thread.sleep(3000);
            for(int i=0;i<100;i++) {
                User user = User.of("user"+i, "baek"+i, Tier.씨앗);
                User saveUser1 = userCService.saveUser(user);
//                Thread.sleep(3000);
            }
            for(int i=0;i<100;i++) {
                followCService.saveFollow("15571557","user"+i);
            }
        }
    }
}
