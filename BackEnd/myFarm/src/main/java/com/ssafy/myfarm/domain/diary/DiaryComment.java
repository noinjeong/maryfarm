package com.ssafy.myfarm.domain.diary;

import com.ssafy.myfarm.domain.BaseTimeEntity;
import com.ssafy.myfarm.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryComment extends BaseTimeEntity {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "diary_comment_id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_id")
    private Diary diary;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    private String content;
    private int likes;

    public static DiaryComment of(Diary diary, User user, String content) {
        DiaryComment diaryComment = new DiaryComment();
        diaryComment.diary = diary;
        diaryComment.user = user;
        diaryComment.content = content;
        diaryComment.likes = 0;
        return diaryComment;
    }
}
