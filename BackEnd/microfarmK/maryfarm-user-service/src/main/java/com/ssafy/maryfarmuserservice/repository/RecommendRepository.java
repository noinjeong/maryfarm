package com.ssafy.maryfarmuserservice.repository;

import com.ssafy.maryfarmuserservice.domain.user.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, String> {
}
