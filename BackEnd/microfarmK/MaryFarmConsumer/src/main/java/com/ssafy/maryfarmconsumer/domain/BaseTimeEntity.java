package com.ssafy.maryfarmconsumer.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.maryfarmconsumer.formatter.LocalDateTimeDeserializer;
import com.ssafy.maryfarmconsumer.formatter.LocalDateTimeSerializer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseTimeEntity {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastModifiedDate;
}
