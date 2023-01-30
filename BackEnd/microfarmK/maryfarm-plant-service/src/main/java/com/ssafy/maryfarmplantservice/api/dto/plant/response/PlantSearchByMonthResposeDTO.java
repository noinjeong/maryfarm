package com.ssafy.maryfarmplantservice.api.dto.plant.response;

import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantSearchByMonthResposeDTO {
    private String id;
    private String userId;
    private String name;
    private String title;
    private LocalDateTime harvestTime;
    private Boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public static PlantSearchByMonthResposeDTO of(Plant plant) {
        PlantSearchByMonthResposeDTO dto = new PlantSearchByMonthResposeDTO();
        dto.id = plant.getId();
        dto.userId = plant.getUserId();
        dto.name = plant.getName();
        dto.title = plant.getTitle();
        dto.harvestTime = plant.getHarvestTime();
        dto.active = plant.getActive();
        dto.createdDate = plant.getCreatedDate();
        dto.lastModifiedDate = plant.getLastModifiedDate();
        return dto;
    }
}
