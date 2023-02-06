package com.ssafy.maryfarmconsumer.repository.board;

import com.ssafy.maryfarmconsumer.query_dto.board.TotalArticleView.SearchArticleByTypeDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SearchArticleByTypeDTORepository extends MongoRepository<SearchArticleByTypeDTO, String> {
    Optional<SearchArticleByTypeDTO> findByType(String type);
}
