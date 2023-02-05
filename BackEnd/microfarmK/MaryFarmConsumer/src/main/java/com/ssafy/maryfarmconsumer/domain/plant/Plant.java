package com.ssafy.maryfarmconsumer.domain.plant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.maryfarmconsumer.domain.BaseTimeEntity;
import com.ssafy.maryfarmconsumer.formatter.LocalDateTimeDeserializer;
import com.ssafy.maryfarmconsumer.formatter.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plant extends BaseTimeEntity implements Serializable {
    private String id;
    private String userId;
    private String name;
    private String title;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime harvestTime;
    private Boolean active;
    // active true/수확안함  false/수확함

    public void setHarvestTime(LocalDateTime harvestTime) {
        this.harvestTime = harvestTime;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public static Plant of(String userId, String title, String name) {
        Plant plant = new Plant();
        plant.userId = userId;
        plant.active = true;
        plant.title = title;
        plant.name = name;
        return plant;
    }
}
