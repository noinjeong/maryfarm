package com.ssafy.maryfarmboardservice.api.dto.article.response;

import com.ssafy.maryfarmboardservice.client.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchArticleResponseDTO {
    private String id;
    private String userName;
    private String title;
    private Integer views;
    private Integer commentCount;
    private LocalDateTime createdDate;
}
