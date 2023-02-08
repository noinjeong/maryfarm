package com.ssafy.myfarm.domain.plant;

import com.ssafy.myfarm.domain.BaseTimeEntity;
import com.ssafy.myfarm.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plant extends BaseTimeEntity {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "plant_id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private String title;
    private LocalDateTime harvestTime;
    private Boolean active;
    // active true/수확안함  false/수확함

    public static Plant of(User user, String title, String name) {
        Plant plant = new Plant();
        plant.user = user;
        plant.active = true;
        plant.title = title;
        plant.name = name;
        return plant;
    }
}
