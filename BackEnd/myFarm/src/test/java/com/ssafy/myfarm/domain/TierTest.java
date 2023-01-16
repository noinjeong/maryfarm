package com.ssafy.myfarm.domain;

import com.ssafy.myfarm.domain.user.Tier;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class TierTest {
    @Autowired
    UserRepository userRepository;

//    @Test
//    @DisplayName("addTier")
//    void addTier() throws Exception {
//        //given
//        User user = User.of("baek", "19960113", "ssafy@ssafy.com", "user");
//        Tier tier = Tier.from("브론즈");
//        user.addTier(tier);
//        //when
//        User saveUser = userRepository.save(user);
//        User findUser = userRepository.findById(saveUser.getId()).get();
//        //then
//        Assertions.assertThat(saveUser).isEqualTo(findUser);
//    }

}