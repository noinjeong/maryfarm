package com.ssafy.maryfarmboardservice.service;

import com.ssafy.maryfarmboardservice.domain.board.Article;
import com.ssafy.maryfarmboardservice.domain.board.ArticleComment;
import com.ssafy.maryfarmboardservice.domain.board.BoardType;
import com.ssafy.maryfarmboardservice.repository.ArticleCommentRepository;
import com.ssafy.maryfarmboardservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    public Article searchArticle(final String id) {
        return articleRepository.findById(id).get();
    }

    @Transactional
    public Article saveArticle(String userId, String userName, String type, String title, String content) {
        Article article = Article.of(userId, userName, BoardType.valueOf(type), title, content);
        Article saveArticle = articleRepository.save(article);
        return saveArticle;
    }

    public List<Article> searchArticleAll(String type) {
        return articleRepository.findByType(BoardType.valueOf(type));
    }

    public List<ArticleComment> searchArticleComment(String articleId) {
        return articleCommentRepository.findByArticle_Id(articleId);
    }

    @Transactional
    public ArticleComment saveArticleComment(String articleId, String userId, String userName, String content) {
        Optional<Article> article = articleRepository.findById(articleId);
        ArticleComment comment = ArticleComment.of(article.get(), userId, userName, content);
        return articleCommentRepository.save(comment);
    }
}
