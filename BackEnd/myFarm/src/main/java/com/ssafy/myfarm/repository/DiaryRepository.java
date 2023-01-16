package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.diary.Diary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query(value = "SELECT d" +
            " FROM Diary d" +
            " JOIN Plant p ON d.plant.id = p.id" +
            " JOIN Follow f ON p.user.id = f.followedUser.id" +
            " WHERE f.followingUser.id = :id AND d.id < :lastPostId")
    List<Diary> findFollowersDiary(@Param("id") Long id, @Param("lastPostId") Long lastPostId, Pageable pageable);
}
