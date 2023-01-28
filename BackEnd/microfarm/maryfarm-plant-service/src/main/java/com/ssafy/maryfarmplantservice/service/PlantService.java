package com.ssafy.maryfarmplantservice.service;

import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import com.ssafy.maryfarmplantservice.repository.PlantRepository;
import com.ssafy.maryfarmplantservice.repository.TagRepository;
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
public class PlantService {
    private final PlantRepository plantRepository;
    private final TagRepository tagRepository;
    @Transactional
    public Plant savePlant(String userId, String title, String name) {
        Optional<User> user = userRepository.findById(userId);
        Plant plant = Plant.of(user.get(), title, name);
        Plant savePlant = plantRepository.save(plant);
        return savePlant;
    }

    public List<Plant> searchPlantsByUserId(String userId) {
        return plantRepository.findByUser_Id(userId);
    }
}
