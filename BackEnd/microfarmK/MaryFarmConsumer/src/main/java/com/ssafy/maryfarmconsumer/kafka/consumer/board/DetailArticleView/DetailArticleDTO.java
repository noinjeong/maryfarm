package com.ssafy.maryfarmconsumer.kafka.consumer.board.DetailArticleView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailArticleDTO {
    private String articleId;
    private String userId;
    private String userName;
    private String type;
    private String title;
    private String content;
    private Integer views;
    private Integer likes;
    private Integer commentCount;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private List<ArticleCommentDTO> comments = new ArrayList<>();
}
