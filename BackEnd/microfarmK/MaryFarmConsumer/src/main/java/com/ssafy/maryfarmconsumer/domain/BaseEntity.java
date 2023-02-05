package com.ssafy.maryfarmconsumer.domain;

import lombok.Getter;


@Getter
public class BaseEntity extends BaseTimeEntity {
    private String createdBy;

    private String lastModifiedBy;
}
