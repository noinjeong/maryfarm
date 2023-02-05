package com.ssafy.maryfarmboardservice.repository.query;

import com.ssafy.maryfarmboardservice.domain.board.Article;
import com.ssafy.maryfarmboardservice.domain.board.BoardType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ArticleQRepository extends MongoRepository<Article, String> {
    List<Article> findByType(String type);
}
