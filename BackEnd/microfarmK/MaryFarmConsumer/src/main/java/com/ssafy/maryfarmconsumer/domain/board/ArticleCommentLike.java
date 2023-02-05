package com.ssafy.maryfarmconsumer.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleCommentLike implements Serializable {
    private String id;
    private ArticleComment comment;
    private String userId;
}
