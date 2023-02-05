package com.ssafy.maryfarmplantservice.domain.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.maryfarmplantservice.domain.BaseTimeEntity;
import com.ssafy.maryfarmplantservice.domain.diary.Diary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity implements Serializable {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "tag_id")
    private String id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_id")
    private Diary diary;
    private String name;

    public static Tag of(Diary diary, String name) {
        Tag tag = new Tag();
        tag.diary = diary;
        diary.getTags().add(tag);
        tag.name = name;
        return tag;
    }
}
