package com.ssafy.maryfarmconsumer.domain.tag;

import com.ssafy.maryfarmconsumer.domain.diary.Diary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag implements Serializable {
    private String id;
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
