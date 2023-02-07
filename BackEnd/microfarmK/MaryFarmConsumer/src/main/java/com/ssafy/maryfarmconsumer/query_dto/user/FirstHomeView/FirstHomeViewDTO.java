package com.ssafy.maryfarmconsumer.query_dto.user.FirstHomeView;

import com.ssafy.maryfarmconsumer.query_dto.plant.HomeDiaryImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "FirstHomeView")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstHomeViewDTO {
    @Id
    private String id;
    private String userId;
    private String latestSystemNotify;
    private List<HomeFollowerImageDTO> followers = new ArrayList<>();
    private List<HomeDiaryImageDTO> diaries = new ArrayList<>();
}
