package com.ssafy.maryfarmconsumer.repository.user;

import com.ssafy.maryfarmconsumer.query_dto.user.FirstHomeView.FirstHomeViewDTO;
import com.ssafy.maryfarmconsumer.query_dto.user.FirstHomeView.HomeFollowerImageDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FirstHomeViewDTORepository extends MongoRepository<FirstHomeViewDTO, String> {
    Optional<FirstHomeViewDTO> findByUserId(String userId);
}
