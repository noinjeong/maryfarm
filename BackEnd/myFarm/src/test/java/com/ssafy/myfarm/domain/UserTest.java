package com.ssafy.myfarm.domain;

import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserTest {
    @Autowired
    UserRepository userRepository;
    
//    @Test
//    @DisplayName("addUser")
//    void addUser() throws Exception {
//        //given
//        User user = User.of("baek", "19960113", "ssafy@ssafy.com", "user");
//        //when
//        User saveUser = userRepository.save(user);
//        Optional<User> findUser = userRepository.findById(saveUser.getId());
//        //then
//        Assertions.assertThat(saveUser).isEqualTo(findUser.get());
//    }
    
}