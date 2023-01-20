package com.ssafy.myfarm.service;

import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.domain.tag.Tag;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.PlantRepository;
import com.ssafy.myfarm.repository.TagRepository;
import com.ssafy.myfarm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    @Transactional
    public Plant savePlant(String userId, String title, String name) {
        Optional<User> user = userRepository.findById(userId);
        Plant plant = Plant.of(user.get(), title, name);
        Plant savePlant = plantRepository.save(plant);
        return savePlant;
    }

    public Plant findPlant(final String id) {
        return plantRepository.findById(id).get();
    }
}
