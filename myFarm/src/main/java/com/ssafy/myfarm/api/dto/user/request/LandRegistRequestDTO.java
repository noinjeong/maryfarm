package com.ssafy.myfarm.api.dto.user.request;

import com.ssafy.myfarm.domain.Land;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandRegistRequestDTO {
    private String id;
    private Land land;
}
