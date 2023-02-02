package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
