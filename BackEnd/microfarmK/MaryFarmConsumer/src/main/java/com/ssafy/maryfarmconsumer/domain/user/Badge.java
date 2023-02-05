package com.ssafy.maryfarmconsumer.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge implements Serializable {
    private String id;
    private String name;

}
