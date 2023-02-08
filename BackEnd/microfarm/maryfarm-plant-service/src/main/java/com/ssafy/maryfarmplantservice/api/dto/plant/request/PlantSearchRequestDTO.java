package com.ssafy.maryfarmplantservice.api.dto.plant.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantSearchRequestDTO {
    private String text;
}
