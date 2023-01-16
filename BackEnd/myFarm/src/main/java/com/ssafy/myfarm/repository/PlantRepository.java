package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    Optional<Plant> findById(Long id);
}
