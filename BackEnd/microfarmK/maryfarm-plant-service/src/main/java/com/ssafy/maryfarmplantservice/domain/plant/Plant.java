package com.ssafy.maryfarmplantservice.domain.plant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.maryfarmplantservice.domain.BaseTimeEntity;
import com.ssafy.maryfarmplantservice.formatter.LocalDateTimeDeserializer;
import com.ssafy.maryfarmplantservice.formatter.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plant extends BaseTimeEntity implements Serializable {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "plant_id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    private String name;
    private String title;
    @Column(name = "harvest_time")
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
