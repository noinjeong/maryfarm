package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.user.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, String> {
}
