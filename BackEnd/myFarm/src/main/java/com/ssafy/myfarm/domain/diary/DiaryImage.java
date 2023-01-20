package com.ssafy.myfarm.domain.diary;

import com.ssafy.myfarm.domain.FileInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryImage {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "diary_image_id")
    private String id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_id")
    private Diary diary;
    @Embedded
    private FileInfo fileInfo;

    public static DiaryImage of(Diary diary, FileInfo fileInfo) {
        DiaryImage diaryImage = new DiaryImage();
        diaryImage.diary = diary;
        diaryImage.fileInfo = fileInfo;
        return diaryImage;
    }
}
