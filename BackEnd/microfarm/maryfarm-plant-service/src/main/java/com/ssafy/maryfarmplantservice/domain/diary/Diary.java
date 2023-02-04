package com.ssafy.maryfarmplantservice.domain.diary;

import com.ssafy.maryfarmplantservice.domain.BaseTimeEntity;
import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import com.ssafy.maryfarmplantservice.domain.tag.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "diary_id")
    private String id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "plant_id")
    private Plant plant;
    private String content;
    private Integer likes;
    private String imagePath;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
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
    public void addLike() {
        this.likes+=1;
    }

}
