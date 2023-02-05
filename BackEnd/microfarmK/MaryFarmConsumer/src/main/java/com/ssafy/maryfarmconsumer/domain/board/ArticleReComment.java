package com.ssafy.maryfarmconsumer.domain.board;

import com.ssafy.maryfarmconsumer.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleReComment extends BaseTimeEntity implements Serializable {
    private String id;
    private ArticleComment comment;
    private String userId;
    private String userName;
    private String content;
}
