package com.ssafy.myfarm.api.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserRequestDTO {
    private Long id;
    private String nickName;
}
