package com.ssafy.myfarm.domain.user;

import com.ssafy.myfarm.domain.FileInfo;
import com.ssafy.myfarm.domain.Land;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
// 프록시 객체 생성만을 위한 생성자라 Protected로 사용제한
// 정적 팩토리 메서드에 필요한 생성자는 롬복 사용하지 않고 직접 구현
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    // 백엔드 서버에서 uuid를 사용한 id 입력
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "user_id")
    private String id;
    private String email;
    private String password;
    private String nickname;
    private String birthday;
    private String role;
    @Embedded
    private Land land;
    @Embedded
    private FileInfo fileInfo;
    @Enumerated(EnumType.STRING)
    private Tier tier;
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setLand(Land land) {
        this.land = land;
    }
    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }
    // 정적 팩토리 메서드
    public static User of(String email, String password, String nickname, String birthday, String role, Tier tier) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.nickname = nickname;
        user.birthday = birthday;
        user.role = role;
        user.tier = tier;
        return user;
    }
}
