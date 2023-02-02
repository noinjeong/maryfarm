package com.ssafy.maryfarmcalendarservice.api.dto.calendar.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCalendarRequestDTO {
    private String userId;
    private Integer year;
    private Integer month;
    private Integer day;
}
