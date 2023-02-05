package com.ssafy.maryfarmconsumer.domain.board;

import com.ssafy.maryfarmconsumer.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleComment extends BaseTimeEntity implements Serializable {
    private String id;
    private Article article;
    private String userId;
    private String userName;
    private String content;
    private Integer likes;

    public static ArticleComment of(Article article, String userId, String userName, String content) {
        ArticleComment comment = new ArticleComment();
        comment.article = article;
        comment.userId = userId;
        comment.userName = userName;
        comment.content = content;
        comment.likes = 0;
        return comment;
    }
}
