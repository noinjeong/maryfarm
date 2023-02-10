package com.numberONE.maryfarm.Retrofit.dto.DetailDiariesPerPlantView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class DetailDiaryDTO {
    private String imagePath;
    private Integer likes;
    private String content;
    private LocalDateTime diaryCreatedDate;
    private List<DetailDiaryCommentDTO> comments = new ArrayList<>();

    public DetailDiaryDTO(Map<Object, Object> payload) {
        this.imagePath = (String) payload.get("image_path");
        this.likes = 0;
        this.content = (String) payload.get("content");
        LocalDateTime diaryCreatedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("created_date")), TimeZone.getDefault().toZoneId());
        this.diaryCreatedDate = diaryCreatedDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Integer getLikes() {
        return likes;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDiaryCreatedDate() {
        return diaryCreatedDate;
    }

    public List<DetailDiaryCommentDTO> getComments() {
        return comments;
    }
}
