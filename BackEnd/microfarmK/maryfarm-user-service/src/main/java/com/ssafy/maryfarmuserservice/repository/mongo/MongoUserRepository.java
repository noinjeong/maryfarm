package com.ssafy.maryfarmuserservice.repository.mongo;

import com.rabbitmq.http.client.domain.UserInfo;
import com.ssafy.maryfarmuserservice.repository.mongo.dto.UserInfoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoUserRepository extends MongoRepository<UserInfoDTO, String> {
}
