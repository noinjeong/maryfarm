package com.ssafy.maryfarmboardservice.api.dto.article.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequestDTO {
    private String userId;
    private String userName;
    private String type;
    private String title;
    private String content;
}
