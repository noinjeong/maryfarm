package com.ssafy.maryfarmplantservice.kafka.dto.plant;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KafkaPlantDTO {
    private String command;
    private String id;
    private String userId;
    private String name;
    private String title;
    private LocalDateTime harvestTime;
    private Boolean active;
}
