package com.ssafy.maryfarmconsumer.query_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeViewDTO {
    // userId
    private String _id;
    List<FollowerImage> followers = new ArrayList<>();
    List<String> top10DiaryRecommended = new ArrayList<>();
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class FollowerImage {
        String nickName;
        String profilePath;
        String latestDiaryImagePath;
    }
}
