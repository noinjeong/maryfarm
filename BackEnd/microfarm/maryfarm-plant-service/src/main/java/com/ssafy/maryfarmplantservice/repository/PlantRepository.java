package com.ssafy.maryfarmplantservice.repository;

import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, String> {
    Optional<Plant> findById(String id);

    List<Plant> findByUser_Id(String id);

//    @Query(value = "SELECT DISTINCT p" +
//            " FROM Plant p" +
//            " RIGHT JOIN Tag t ON t.plant.id = p.id" +
//            " WHERE t.name = :name")
//    List<Plant> findPlantByTag(@Param("name") String text);
}
