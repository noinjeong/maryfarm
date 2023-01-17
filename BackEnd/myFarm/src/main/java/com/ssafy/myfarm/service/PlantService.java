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
    public Plant savePlant(Long userId, String name) {
        Optional<User> user = userRepository.findById(userId);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Plant plant = Plant.of(user.get(), name+simpleDateFormat.format(date));
        Plant savePlant = plantRepository.save(plant);
        // 태그 등록 시작
        Tag tag = Tag.of(savePlant, name);
        Tag saveTag = tagRepository.save(tag);
        // 태그 등록 종료
        return savePlant;
    }

    public Plant findPlant(final Long id) {
        return plantRepository.findById(id).get();
    }

    public List<Plant> searchPlantByTag(String text) {
        return plantRepository.findPlantByTag(text);
    }
}
