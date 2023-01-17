package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.diary.DiaryLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, Long> {
}
