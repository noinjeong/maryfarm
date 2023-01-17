package com.ssafy.myfarm.service;

import com.ssafy.myfarm.domain.Land;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User joinUser(final User user) {
        User saveUser = userRepository.save(user);
        return saveUser;
    }
    public User findUser(final Long id) { return userRepository.findById(id).get(); }
    public User loginUser(final String email, final String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent() && password.equals(user.get().getPassword())) {
            return user.get();
        }
        return null;
    }
    @Transactional
    public void updateUser(final Long id, final String nickname) {
        Optional<User> user = userRepository.findById(id);
        user.get().setNickname(nickname);
    }

    @Transactional
    public User registLand(Long id, Land land) {
        Optional<User> user = userRepository.findById(id);
        user.get().setLand(land);
        return user.get();
    }
}
