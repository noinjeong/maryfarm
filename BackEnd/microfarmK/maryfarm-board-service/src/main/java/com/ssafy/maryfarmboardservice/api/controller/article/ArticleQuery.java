package com.ssafy.maryfarmboardservice.api.controller.article;

import com.ssafy.maryfarmboardservice.api.dto.article.request.SearchArticleRequestDTO;
import com.ssafy.maryfarmboardservice.api.dto.article.response.SearchArticleResponseDTO;
import com.ssafy.maryfarmboardservice.api.dto.query.DetailArticleView.DetailArticleDTO;
import com.ssafy.maryfarmboardservice.api.dto.query.TotalArticleView.ArticleDTO;
import com.ssafy.maryfarmboardservice.domain.board.Article;
import com.ssafy.maryfarmboardservice.mongo.MongoCRUD;
import com.ssafy.maryfarmboardservice.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
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
    private final MongoCRUD mongoCRUD;
    private final ArticleService articleService;
    @Operation(summary = "전체 게시글 조회", description = "전체 게시글을 조회합니다.", tags = { "Board Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ArticleDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/board/search")
    public ResponseEntity<?> searchArticleAll(@RequestBody SearchArticleRequestDTO dto) throws IOException {
        List<Document> documents = mongoCRUD.findData("type", dto.getType(), "articleGroupByType");
        List<ArticleDTO> resultDtos = (List<ArticleDTO>) documents.get(0).get("articles");
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.", tags = { "Board Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = DetailArticleDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/board/search/{articleId}")
    public ResponseEntity<?> searchDetailArticle(@PathVariable("articleId") String articleId) throws IOException {
        List<Document> documents = mongoCRUD.findData("articleId", articleId, "detailArticle");
        articleService.addViews(articleId);
        List<DetailArticleDTO> resultDtos = (List<DetailArticleDTO>) documents.get(0).get("articles");
        return ResponseEntity.ok(resultDtos);
    }
}
