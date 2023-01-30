package com.ssafy.maryfarmnotifyservice.kafka.dto.notify;

import com.ssafy.maryfarmnotifyservice.domain.notify.AlarmType;
import lombok.Data;

@Data
public class KafkaNotifyDTO {
    private String command;
    private String id;
    private String userId;
    private AlarmType type;
    private String content;
    private Boolean active;
}
