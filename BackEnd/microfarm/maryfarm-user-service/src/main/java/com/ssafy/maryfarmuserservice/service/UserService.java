package com.ssafy.maryfarmuserservice.service;

import com.ssafy.maryfarmuserservice.domain.Land;
import com.ssafy.maryfarmuserservice.domain.user.Recommend;
import com.ssafy.maryfarmuserservice.domain.user.User;
import com.ssafy.maryfarmuserservice.repository.RecommendRepository;
import com.ssafy.maryfarmuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RecommendRepository recommendRepository;

    @Transactional
    public User saveUser(final User user) {
        User saveUser = userRepository.save(user);
        return saveUser;
    }
    public User findUser(final String id) { return userRepository.findById(id).get(); }
    public User loginUser(final String kakaoid) {
        Optional<User> user = userRepository.findById(kakaoid);
        if(user.isPresent()) {
            return user.get();
        }
        return null;
    }
    @Transactional
    public User updateUser(final String id, final String nickname, final String profilePath) {
        Optional<User> user = userRepository.findById(id);
        user.get().setNickname(nickname);
        user.get().setProfilePath(profilePath);
        return user.get();
    }

    @Transactional
    public User registLand(String id, String latitude, String longitude) {
        Optional<User> user = userRepository.findById(id);
        Land land = new Land(latitude, longitude);
        user.get().setLand(land);
        return user.get();
    }

    public void saveRecommend(String id, String fullCode) {
        Optional<User> user = userRepository.findById(id);
        Recommend recommend = Recommend.of(user.get(), fullCode);
        recommendRepository.save(recommend);
    }
}
