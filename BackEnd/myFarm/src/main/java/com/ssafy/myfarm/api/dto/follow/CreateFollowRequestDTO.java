package com.ssafy.myfarm.api.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFollowRequestDTO {
    private Long senderId;
    private Long receiverId;
}
