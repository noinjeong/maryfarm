package com.ssafy.maryfarmboardservice.repository.query;

import com.ssafy.maryfarmboardservice.domain.board.ArticleComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface ArticleCommentQRepository extends MongoRepository<ArticleComment, String> {
}
