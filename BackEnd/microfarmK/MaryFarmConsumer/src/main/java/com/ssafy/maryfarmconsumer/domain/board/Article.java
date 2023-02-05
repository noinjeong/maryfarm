package com.ssafy.maryfarmconsumer.domain.board;

import com.ssafy.maryfarmconsumer.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTimeEntity implements Serializable {
    private String id;
    private String userId;
    private String userName;
    private BoardType type;
    private String title;
    private String content;
    private Integer views;
    private Integer likes;
    private Integer commentCount;
     public static Article of(String userId, String userName, BoardType type, String title, String content) {
        Article article = new Article();
        article.userId = userId;
        article.userName = userName;
        article.type = type;
        article.title = title;
        article.content = content;
        article.views = 0;
        article.likes = 0;
        article.commentCount = 0;
        return article;
    }

    public void addViews(Integer viewCnt) {
        this.views+=viewCnt;
    }
}
