package com.ssafy.maryfarmboardservice.api.dto.article.response;

import com.ssafy.maryfarmboardservice.api.dto.comment.response.CommentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDetailArticleResponseDTO {
    private String title;
    private String content;
    private Integer views;
    private Integer likes;

    private List<CommentResponseDTO> comments = new ArrayList<>();
}
