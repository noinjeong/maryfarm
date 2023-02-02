package com.ssafy.maryfarmcalendarservice.api.dto.calendar.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistCalendarRequestDTO {
    private String plantId;
    private LocalDate registDate;
    private String userId;
    private Boolean water;
    private Boolean branch;
    private Boolean nutrients;
    private Boolean division;
    private String memo;
}
