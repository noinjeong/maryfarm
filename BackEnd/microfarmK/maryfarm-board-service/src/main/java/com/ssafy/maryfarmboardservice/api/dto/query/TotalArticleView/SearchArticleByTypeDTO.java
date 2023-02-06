package com.ssafy.maryfarmboardservice.api.dto.query.TotalArticleView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchArticleByTypeDTO {
    private String type;
    private List<ArticleDTO> articles = new ArrayList<>();

}
