package com.ssafy.maryfarmconsumer.query_dto.DetailArticleView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentDTO {
    private String commentId;
    private String userId;
    private String userName;
    private String content;
    private Integer likes;
    private String profilePath;

    public ArticleCommentDTO(Map<Object, Object> payload) {
        this.commentId = (String) payload.get("article_comment_id");
        this.userId = (String) payload.get("user_id");
        this.userName = (String) payload.get("user_name");
        this.content = (String) payload.get("content");
        this.profilePath = (String) payload.get("profile_path");
        this.likes = 0;
    }
}
