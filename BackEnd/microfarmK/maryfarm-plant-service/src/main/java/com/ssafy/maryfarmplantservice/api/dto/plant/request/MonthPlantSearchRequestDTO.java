package com.ssafy.maryfarmplantservice.api.dto.plant.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthPlantSearchRequestDTO {
    private String userId;
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDateTime yearMonth;
}
