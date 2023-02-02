package com.ssafy.maryfarmboardservice.api.controller.article;

import com.ssafy.maryfarmboardservice.api.dto.article.request.CreateArticleRequestDTO;
import com.ssafy.maryfarmboardservice.api.dto.article.request.SearchArticleRequestDTO;
import com.ssafy.maryfarmboardservice.api.dto.article.response.SearchArticleResponseDTO;
import com.ssafy.maryfarmboardservice.api.dto.article.response.SearchDetailArticleResponseDTO;
import com.ssafy.maryfarmboardservice.api.dto.comment.request.ArticleCommentRequestDTO;
import com.ssafy.maryfarmboardservice.api.dto.comment.response.CommentResponseDTO;
import com.ssafy.maryfarmboardservice.client.dto.user.UserResponseDTO;
import com.ssafy.maryfarmboardservice.client.service.user.UserServiceClient;
import com.ssafy.maryfarmboardservice.domain.board.Article;
import com.ssafy.maryfarmboardservice.domain.board.ArticleComment;
import com.ssafy.maryfarmboardservice.kafka.dto.Status;
import com.ssafy.maryfarmboardservice.kafka.producer.article.ArticleProducer;
import com.ssafy.maryfarmboardservice.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ArticleController {
    private final ArticleService articleService;
    private final UserServiceClient userServiceClient;
    private final ArticleProducer articleProducer;
    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.", tags = { "Board Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/board/create")
    public ResponseEntity<?> createArticle(@RequestBody CreateArticleRequestDTO dto) throws IOException {
        Article article = articleService.saveArticle(dto.getUserId(),dto.getUserName(),dto.getType(),dto.getTitle(),dto.getContent());
        articleProducer.send("article",article, Status.C);
        return ResponseEntity.ok(article.getId());
    }

    @Operation(summary = "게시글 댓글 작성", description = "특정 게시글의 댓글을 작성합니다.", tags = { "Board Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/board/comment/create")
    public ResponseEntity<?> createArticleComment(@RequestBody ArticleCommentRequestDTO dto) throws IOException {
        ArticleComment comment = articleService.saveArticleComment(dto.getArticleId(),dto.getUserId(),dto.getUserName(),dto.getContent());
        return ResponseEntity.ok(comment.getId());
    }

    @Operation(summary = "전체 게시글 조회", description = "전체 게시글을 조회합니다.", tags = { "Board Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SearchArticleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/board/search")
    public ResponseEntity<?> searchArticleAll(@RequestBody SearchArticleRequestDTO dto) throws IOException {
        List<Article> list = articleService.searchArticleAll(dto.getType());
        List<SearchArticleResponseDTO> resultDtos = new ArrayList<>();
        for(Article a : list) {
            SearchArticleResponseDTO searchArticleResponseDTO = SearchArticleResponseDTO.builder()
                    .id(a.getId()).userName(a.getUserName())
                    .title(a.getTitle()).views(a.getViews())
                    .commentCount(a.getCommentCount()).createdDate(a.getCreatedDate()).build();
            resultDtos.add(searchArticleResponseDTO);
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.", tags = { "Board Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SearchDetailArticleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/board/search/{articleId}")
    public ResponseEntity<?> searchDetailArticle(@PathVariable("articleId") String articleId) throws IOException {
        Article article = articleService.searchArticle(articleId);
        articleService.addViews(articleId);
        List<ArticleComment> comments = articleService.searchArticleComment(articleId);
        List<CommentResponseDTO> commentDtos = new ArrayList<>();
        for(ArticleComment c : comments) {
            CommentResponseDTO responseDTO = CommentResponseDTO.builder()
                    .userName(c.getUserName())
                    .content(c.getContent())
                    .likes(c.getLikes())
                    .build();
            commentDtos.add(responseDTO);
        }
        SearchDetailArticleResponseDTO resultDto = SearchDetailArticleResponseDTO.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .views(article.getViews())
                .likes(article.getLikes())
                .comments(commentDtos).build();
        return ResponseEntity.ok(resultDto);

    }
}
