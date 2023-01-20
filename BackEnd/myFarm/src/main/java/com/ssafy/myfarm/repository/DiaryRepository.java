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
            " JOIN FETCH Plant p ON d.plant.id = p.id" +
            " JOIN Follow f ON p.user.id = f.receiverUser.id" +
            " JOIN FETCH User u ON p.user.id = u.id" +
            " WHERE f.senderUser.id = :id")
    List<Diary> findFollowersDiary(@Param("id") String id);

    // FROM의 주체와 SELECT로 가져오는 엔티티가 달라서 안될수도...
    @Query(value = "SELECT d" +
            " FROM Diary d" +
            " JOIN FETCH Tag t ON t.diary.id = d.id" +
            " JOIN FETCH Plant p ON p.id = d.plant.id" +
            " JOIN FETCH User u ON u.id = p.user.id" +
            " WHERE t.name = :text" +
            " GROUP BY d.plant" +
            " ORDER BY d.createdDate desc" +
            " LIMIT 1")
    List<Diary> findDiarysByTag(@Param("text") String text);

    @Query(value = "SELECT d" +
            " FROM Diary d" +
            " JOIN FETCH Plant p ON p.id = d.plant.id" +
            " WHERE d.plant.id = :id")
    List<Diary> findDiaryGroup(@Param("id") String id);
}
