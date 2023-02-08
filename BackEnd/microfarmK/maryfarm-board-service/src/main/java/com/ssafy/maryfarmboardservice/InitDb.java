package com.ssafy.maryfarmboardservice;

import com.ssafy.maryfarmboardservice.domain.board.Article;
import com.ssafy.maryfarmboardservice.domain.board.BoardType;
import com.ssafy.maryfarmboardservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final ArticleRepository articleRepository;
        public void dbInit() {
            Article article1 = Article.of("123456", "baek", BoardType.Free, "제목입니다", "내용입니다.");
            Article article2 = Article.of("1234567", "seung", BoardType.Free, "제목입니다", "내용입니다.");
            articleRepository.save(article1);
            articleRepository.save(article2);
        }
    }
}
