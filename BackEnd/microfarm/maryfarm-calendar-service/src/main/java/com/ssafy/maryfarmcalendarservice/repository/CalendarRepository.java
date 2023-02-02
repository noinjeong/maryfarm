package com.ssafy.maryfarmcalendarservice.repository;

import com.ssafy.maryfarmcalendarservice.domain.calendar.Calendar;
import com.ssafy.maryfarmcalendarservice.domain.calendar.CalendarID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, CalendarID> {
}
