package com.ssafy.myfarm.domain.tag;

import com.ssafy.myfarm.domain.plant.Plant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    public static Tag of(Plant plant, String name) {
        Tag tag = new Tag();
        tag.plant = plant;
        tag.name = name;
        return tag;
    }
}
