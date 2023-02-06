package com.ssafy.maryfarmconsumer.query_dto.board.TotalArticleView;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "articleGroupByType")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchArticleByTypeDTO {
    @Id
    private String id;
    private String type;
    private List<ArticleDTO> articles = new ArrayList<>();

}
