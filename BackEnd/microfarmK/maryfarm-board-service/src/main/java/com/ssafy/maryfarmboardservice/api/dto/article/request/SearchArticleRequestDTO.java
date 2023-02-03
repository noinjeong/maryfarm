package com.ssafy.maryfarmboardservice.api.dto.article.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchArticleRequestDTO {
    private String type;
}
