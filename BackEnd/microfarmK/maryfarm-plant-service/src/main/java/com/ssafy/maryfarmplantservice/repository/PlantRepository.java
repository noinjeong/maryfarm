package com.ssafy.maryfarmplantservice.repository;

import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, String> {
    Optional<Plant> findById(String id);

    List<Plant> findByUserId(String id);

    @Query(value = "SELECT p" +
        " FROM Plant p" +
        " WHERE (p.userId = :user)" +
        " AND ((p.active = true) OR (p.harvestTime = :yearMonth))")
    List<Plant> findPlantByMonth(String userId, LocalDateTime yearMonth);

//    @Query(value = "SELECT DISTINCT p" +
//            " FROM Plant p" +
//            " RIGHT JOIN Tag t ON t.plant.id = p.id" +
//            " WHERE t.name = :name")
//    List<Plant> findPlantByTag(@Param("name") String text);
}
