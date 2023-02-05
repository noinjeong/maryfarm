package com.ssafy.maryfarmconsumer.domain.calendar;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar implements Serializable {
    private CalendarID id;
    private String userId;
    private Boolean water;
    private Boolean branch;
    private Boolean nutrients;
    private Boolean division;
    private String memo;
    public static Calendar of(String plantId, LocalDate registDate, String userId,
                              Boolean water, Boolean branch, Boolean nutrients,
                              Boolean division, String memo) {
        Calendar calendar = new Calendar();
        calendar.id = new CalendarID(plantId,registDate);
        calendar.userId = userId;
        calendar.water = water;
        calendar.branch = branch;
        calendar.nutrients = nutrients;
        calendar.division = division;
        calendar.memo = memo;
        return calendar;
    }
}
