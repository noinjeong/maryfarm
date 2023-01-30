package com.ssafy.maryfarmcalendarservice.domain.calendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarID implements Serializable {
    @Column(name = "plant_id")
    private String plantId;
    @Column(name = "regist_date")
    private LocalDate registDate;
}
