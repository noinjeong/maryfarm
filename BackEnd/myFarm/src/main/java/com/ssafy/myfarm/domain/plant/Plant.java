package com.ssafy.myfarm.domain.plant;

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
public class Plant extends BaseTimeEntity {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "plant_id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    private String name;

    public static Plant of(User user, String name) {
        Plant plant = new Plant();
        plant.user = user;
        plant.name = name;
        return plant;
    }
}
