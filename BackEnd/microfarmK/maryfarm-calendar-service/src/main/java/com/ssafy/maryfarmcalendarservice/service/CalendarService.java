package com.ssafy.maryfarmcalendarservice.service;

import com.ssafy.maryfarmcalendarservice.api.dto.calendar.request.RegistCalendarRequestDTO;
import com.ssafy.maryfarmcalendarservice.api.dto.calendar.request.SearchCalendarByDayRequestDTO;
import com.ssafy.maryfarmcalendarservice.domain.calendar.Calendar;
import com.ssafy.maryfarmcalendarservice.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public List<Calendar> searchCalendarByDay(SearchCalendarByDayRequestDTO dto) {
        return calendarRepository.findCalendarByDay(dto.getUserId(),dto.getYear(),dto.getMonth(),dto.getDay());
    }

    @Transactional
    public Calendar registCalendar(RegistCalendarRequestDTO dto) {
        Calendar calendar = Calendar.of(dto.getPlantId(), dto.getRegistDate(), dto.getUserId(), dto.getWater(),
                dto.getBranch(), dto.getNutrients(), dto.getDivision(), dto.getMemo());
        return calendarRepository.save(calendar);
    }

}
