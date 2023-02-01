package com.ssafy.maryfarmcalendarservice.api.dto.calendar.response;

import com.ssafy.maryfarmcalendarservice.domain.calendar.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCalendarResponseDTO {
    private String plantId;
    private Boolean water;
    private Boolean branch;
    private Boolean nutrients;
    private Boolean division;
    private String memo;

    public static SearchCalendarResponseDTO of(Calendar calendar) {
        SearchCalendarResponseDTO dto = new SearchCalendarResponseDTO();
        dto.plantId = calendar.getId().getPlantId();
        dto.water = calendar.getWater();
        dto.branch = calendar.getBranch();
        dto.nutrients = calendar.getNutrients();
        dto.division = calendar.getDivision();
        dto.memo = calendar.getMemo();
        return dto;
    }
}
