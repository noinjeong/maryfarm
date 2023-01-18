package com.ssafy.myfarm.domain.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.myfarm.domain.diary.Diary;
import com.ssafy.myfarm.domain.plant.Plant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "tag_id")
    private String id;
    private String name;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public static Tag of(Diary diary, String name) {
        Tag tag = new Tag();
        tag.diary = diary;
        diary.getTags().add(tag);
        tag.name = name;
        return tag;
    }
}
