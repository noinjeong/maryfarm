package com.ssafy.maryfarmconsumer.repository.calendar;

import com.ssafy.maryfarmconsumer.query_dto.calendar.CalendarPerDayView.CalendarPerDayDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CalendarPerDayDTORepository extends MongoRepository<CalendarPerDayDTO, String> {
    Optional<CalendarPerDayDTO> findByPlantIdAndYearAndMonthAndDay(String plantId, Integer year, Integer month, Integer day);
}
