package com.ssafy.myfarm.api.dto.plant.response;

import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantResponseDTO {
    private Long id;
    private String name;

    public static PlantResponseDTO of(Plant plant) {
        PlantResponseDTO dto = new PlantResponseDTO();
        dto.id = plant.getId();
        dto.name = plant.getName();
        return dto;
    }
}
