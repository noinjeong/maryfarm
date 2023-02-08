package com.ssafy.maryfarmplantservice.repository;

import com.ssafy.maryfarmplantservice.domain.diary.DiaryLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, String> {
}
