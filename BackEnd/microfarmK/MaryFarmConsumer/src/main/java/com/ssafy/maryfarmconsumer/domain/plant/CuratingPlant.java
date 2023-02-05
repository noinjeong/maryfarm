package com.ssafy.maryfarmconsumer.domain.plant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CuratingPlant implements Serializable {
    private String name;
}
