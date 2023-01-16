package com.ssafy.myfarm.domain.diary;

import com.ssafy.myfarm.domain.BaseTimeEntity;
import com.ssafy.myfarm.domain.plant.Plant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;
    private String title;
    private String content;
    private int likes;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    public static Diary of(String title, String content, Plant plant) {
        Diary diary = new Diary();
        diary.title = title;
        diary.content = content;
        diary.likes = 0;
        diary.plant = plant;
        return diary;
    }
}
