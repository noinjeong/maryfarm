package com.ssafy.myfarm.service;

import com.ssafy.myfarm.domain.board.Article;
import com.ssafy.myfarm.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public Article write(final Article article) {
        Article saveArticle = articleRepository.save(article);
        return saveArticle;
    }
    public Article findArticle(final Long id) {
        return articleRepository.findById(id).get();
    }
}
