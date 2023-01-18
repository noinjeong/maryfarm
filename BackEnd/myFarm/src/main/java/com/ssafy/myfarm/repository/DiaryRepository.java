package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.diary.Diary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, String> {

//    @Query(value = "SELECT d" +
//            " FROM Diary d" +
//            " JOIN Plant p ON d.plant.id = p.id" +
//            " JOIN Follow f ON p.user.id = f.receiverUser.id" +
//            " WHERE f.senderUser.id = :id AND d.id < :lastPostId")
//    List<Diary> findFollowersDiary(@Param("id") Long id, @Param("lastPostId") Long lastPostId, Pageable pageable);

    @Query(value = "SELECT d" +
            " FROM Diary d" +
            " JOIN Plant p ON d.plant.id = p.id" +
            " JOIN Follow f ON p.user.id = f.receiverUser.id" +
            " WHERE f.senderUser.id = :id")
    List<Diary> findFollowersDiary(@Param("id") String id);

    @Query(value = "SELECT d" +
            " FROM Diary d" +
            " JOIN Plant p ON p.id = d.plant.id" +
            " WHERE p.id = :id")
    List<Diary> findDiarysByPlant(@Param("id") String id);
}
