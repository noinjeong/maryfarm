package com.ssafy.maryfarmplantservice.repository;

import com.ssafy.maryfarmplantservice.domain.diary.DiaryComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment, String> {
}
