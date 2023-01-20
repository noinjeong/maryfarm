package com.ssafy.myfarm.api.dto.plant.response;

import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantResponseDTO {
    private String plantid;
    private User user;
    private String name;
    private String title;
    private LocalDateTime harvesttime;
    private boolean active;
    public static PlantResponseDTO of(Plant plant) {
        PlantResponseDTO dto = new PlantResponseDTO();
        dto.plantid = plant.getId();
        dto.user = plant.getUser();
        dto.name = plant.getName();
        dto.title = plant.getTitle();;
        dto.harvesttime = plant.getHarvestTime();
        dto.active = plant.getActive();
        return dto;
    }
}
