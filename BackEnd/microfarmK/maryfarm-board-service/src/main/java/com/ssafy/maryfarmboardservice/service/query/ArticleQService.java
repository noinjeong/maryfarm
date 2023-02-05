package com.ssafy.maryfarmboardservice.service.query;

import com.ssafy.maryfarmboardservice.domain.board.Article;
import com.ssafy.maryfarmboardservice.domain.board.BoardType;
import com.ssafy.maryfarmboardservice.repository.query.ArticleQRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ArticleQService {
    private final ArticleQRepository articleQRepository;

    @Cacheable(value = "article", key = "#id")
    public Article searchArticle(final String id) {
        return articleQRepository.findById(id).get();
    }

    public List<Article> searchArticleAll(String type) {
//        Article temp = Article.of("124850", "kim", BoardType.Free, "title", "content");
//        articleQRepository.save(temp);
//        List<Article> result = new ArrayList<>();
//        result.add(temp);
        return articleQRepository.findByType(type);
//        return articleQRepository.findByType(type);
//        return result;
    }
}
