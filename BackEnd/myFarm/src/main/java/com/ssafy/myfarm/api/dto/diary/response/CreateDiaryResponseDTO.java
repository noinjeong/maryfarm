package com.ssafy.myfarm.api.dto.diary.response;

import com.ssafy.myfarm.domain.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiaryResponseDTO {
    private FileInfo fileInfo;
}
