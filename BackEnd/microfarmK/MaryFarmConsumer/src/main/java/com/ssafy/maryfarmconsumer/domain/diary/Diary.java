package com.ssafy.maryfarmconsumer.domain.diary;

import com.ssafy.maryfarmconsumer.domain.BaseTimeEntity;
import com.ssafy.maryfarmconsumer.domain.plant.Plant;
import com.ssafy.maryfarmconsumer.domain.tag.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity implements Serializable {
    private String id;
    private Plant plant;
    private String content;
    private Integer likes;
    private String imagePath;

    private List<Tag> tags = new ArrayList<>();

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Diary of(Plant plant, String content, String imagePath) {
        Diary diary = new Diary();
        diary.plant = plant;
        diary.content = content;
        diary.likes = 0;
        diary.imagePath = imagePath;
        return diary;
    }

    // 비즈니스 로직
    public void addLike(Integer cnt) {
        this.likes+=cnt;
    }

}
