package com.ssafy.maryfarmplantservice.repository;

import com.ssafy.maryfarmplantservice.domain.diary.DiaryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment, String> {
    List<DiaryComment> findByDiary_Id(String diaryId);
}
