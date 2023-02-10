package com.numberONE.maryfarm.Retrofit.dto.DetailDiariesPerPlantView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class DetailDiariesPerPlantDTO {

    private String id;
    private String plantId;
    private String title;
    private LocalDateTime plantCreatedDate;
    private LocalDateTime harvestDate;
    private List<DetailDiaryDTO> diaries = new ArrayList<>();

    public DetailDiariesPerPlantDTO(Map<Object, Object> payload) {
        this.plantId = (String) payload.get("plant_id");
        this.title = (String) payload.get("title");
        LocalDateTime plantCreatedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("plant_created_date")), TimeZone.getDefault().toZoneId());
        this.plantCreatedDate = plantCreatedDate;
        if(!((String) payload.get("harvest_date")==null)) {
          LocalDateTime harvestDate = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("harvest_date")), TimeZone.getDefault().toZoneId());
            this.harvestDate = harvestDate;
        }
    }

    public String getId() {
        return id;
    }

    public String getPlantId() {
        return plantId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getPlantCreatedDate() {
        return plantCreatedDate;
    }

    public LocalDateTime getHarvestDate() {
        return harvestDate;
    }

    public List<DetailDiaryDTO> getDiaries() {
        return diaries;
    }
}
