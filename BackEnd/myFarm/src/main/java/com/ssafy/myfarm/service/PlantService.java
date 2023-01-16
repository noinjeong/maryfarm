package com.ssafy.myfarm.service;

import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;

    public Plant regist(final Plant plant) {
        Plant savePlant = plantRepository.save(plant);
        return savePlant;
    }
    public Plant findPlant(final String id) {
        return plantRepository.findById(id).get();
    }
}
