package com.ssafy.maryfarmboardservice.api.controller.article;

import com.ssafy.maryfarmboardservice.api.dto.article.request.SearchArticleRequestDTO;
import com.ssafy.maryfarmboardservice.api.dto.article.response.SearchArticleResponseDTO;
import com.ssafy.maryfarmboardservice.api.dto.article.response.SearchDetailArticleResponseDTO;
import com.ssafy.maryfarmboardservice.api.dto.comment.response.CommentResponseDTO;
import com.ssafy.maryfarmboardservice.client.service.user.UserServiceClient;
import com.ssafy.maryfarmboardservice.domain.board.Article;
import com.ssafy.maryfarmboardservice.domain.board.ArticleComment;
import com.ssafy.maryfarmboardservice.kafka.producer.article.ArticleProducer;
import com.ssafy.maryfarmboardservice.service.command.ArticleCService;
import com.ssafy.maryfarmboardservice.service.query.ArticleQService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ArticleQuery {
    private final ArticleQService articleQService;
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
//        List<Article> list = articleQService.searchArticleAll(dto.getType());
//        List<SearchArticleResponseDTO> resultDtos = new ArrayList<>();
//        for(Article a : list) {
//            SearchArticleResponseDTO searchArticleResponseDTO = SearchArticleResponseDTO.builder()
//                    .id(a.getId()).userName(a.getUserName())
//                    .title(a.getTitle()).views(a.getViews())
//                    .commentCount(a.getCommentCount()).createdDate(a.getCreatedDate()).build();
//            resultDtos.add(searchArticleResponseDTO);
//        }
//        return ResponseEntity.ok(resultDtos);
        Article article = articleQService.searchArticle(dto.getType());
        return ResponseEntity.ok(article);
    }

//    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.", tags = { "Board Controller" })
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(schema = @Schema(implementation = SearchDetailArticleResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
//            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
//    })
//    @GetMapping("/board/search/{articleId}")
//    public ResponseEntity<?> searchDetailArticle(@PathVariable("articleId") String articleId) throws IOException {
//        Article article = articleQService.searchArticle(articleId);
//        articleCService.addViews(articleId);
//        List<ArticleComment> comments = articleCService.searchArticleComment(articleId);
//        List<CommentResponseDTO> commentDtos = new ArrayList<>();
//        for(ArticleComment c : comments) {
//            CommentResponseDTO responseDTO = CommentResponseDTO.builder()
//                    .userName(c.getUserName())
//                    .content(c.getContent())
//                    .likes(c.getLikes())
//                    .build();
//            commentDtos.add(responseDTO);
//        }
//        SearchDetailArticleResponseDTO resultDto = SearchDetailArticleResponseDTO.builder()
//                .title(article.getTitle())
//                .content(article.getContent())
//                .views(article.getViews())
//                .likes(article.getLikes())
//                .comments(commentDtos).build();
//        return ResponseEntity.ok(resultDto);
//
//    }
}
