package com.ssafy.maryfarmplantservice.domain.diary;

import com.ssafy.maryfarmplantservice.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
<<<<<<< HEAD:BackEnd/microfarmK/maryfarm-plant-service/src/main/java/com/ssafy/maryfarmplantservice/domain/diary/DiaryLike.java
public class DiaryLike implements Serializable {
=======
public class DiaryLike extends BaseTimeEntity implements Serializable {
>>>>>>> back:BackEnd/maryfarm-plant-service/src/main/java/com/ssafy/maryfarmplantservice/domain/diary/DiaryLike.java
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "diary_like_id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private String userName;

    public static DiaryLike of(String userId, Diary diary, String userName) {
        DiaryLike diaryLike = new DiaryLike();
        diaryLike.userId = userId;
        diaryLike.diary = diary;
        diaryLike.userName = userName;
        return diaryLike;
    }
}
