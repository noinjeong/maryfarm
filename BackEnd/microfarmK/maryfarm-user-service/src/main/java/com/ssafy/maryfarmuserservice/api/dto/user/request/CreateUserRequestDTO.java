package com.ssafy.maryfarmuserservice.api.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {
    private String kakaoId;
    private String nickname;
    private String profilePath;
}
