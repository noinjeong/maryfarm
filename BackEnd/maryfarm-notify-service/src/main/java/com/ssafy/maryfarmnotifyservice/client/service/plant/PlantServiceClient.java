package com.ssafy.maryfarmnotifyservice.client.service.plant;

import com.ssafy.maryfarmnotifyservice.client.dto.plant.DiaryResponseDTO;
import com.ssafy.maryfarmnotifyservice.client.dto.plant.PlantResponseDTO;
import com.ssafy.maryfarmnotifyservice.client.dto.user.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@FeignClient(name="maryfarm-plant-service")
public interface PlantServiceClient {

    @GetMapping("/diary/search/{diaryId}")
    public DiaryResponseDTO DiarySearch(@PathVariable("diaryId") String diaryId);
}
