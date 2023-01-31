package com.ssafy.maryfarmboardservice.api.controller.board;

import com.ssafy.maryfarmboardservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class BoardController {
    private final ArticleService articleService;
}
