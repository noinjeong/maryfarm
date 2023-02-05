package com.ssafy.maryfarmconsumer.domain.diary;

import com.ssafy.maryfarmconsumer.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryComment extends BaseTimeEntity implements Serializable {
    private String id;
    private Diary diary;
    private String userId;
    private String content;
    private Integer likes;

    public static DiaryComment of(Diary diary, String userId, String content) {
        DiaryComment diaryComment = new DiaryComment();
        diaryComment.diary = diary;
        diaryComment.userId = userId;
        diaryComment.content = content;
        diaryComment.likes = 0;
        return diaryComment;
    }
}
