package com.ssafy.maryfarmconsumer.domain.diary;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryLike implements Serializable {
    private String id;
    private String userId;
    private Diary diary;

    public static DiaryLike of(String userId, Diary diary) {
        DiaryLike diaryLike = new DiaryLike();
        diaryLike.userId = userId;
        diaryLike.diary = diary;
        return diaryLike;
    }
}
