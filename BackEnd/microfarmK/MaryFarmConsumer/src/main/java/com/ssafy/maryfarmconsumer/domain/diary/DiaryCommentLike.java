package com.ssafy.maryfarmconsumer.domain.diary;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryCommentLike implements Serializable {
    private String id;
    private String userId;
    private DiaryComment comment;
}
