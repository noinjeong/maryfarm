package com.ssafy.maryfarmboardservice.repository;

import com.ssafy.maryfarmboardservice.domain.board.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment,String> {
    List<ArticleComment> findByArticle_Id(String articleId);
}
