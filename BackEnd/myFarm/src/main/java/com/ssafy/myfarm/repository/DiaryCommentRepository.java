package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.diary.DiaryComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment, String> {
}
