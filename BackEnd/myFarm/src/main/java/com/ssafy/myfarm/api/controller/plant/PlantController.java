package com.ssafy.myfarm.api.controller.plant;

import com.ssafy.myfarm.api.dto.plant.request.PlantSearchRequestDTO;
import com.ssafy.myfarm.api.dto.plant.response.PlantResponseDTO;
import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.service.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PlantController {
    private final PlantService plantService;

    @PostMapping("/plant/search")
    public ResponseEntity<?> searchPlant(@RequestBody PlantSearchRequestDTO dto) {
        List<Plant> list = plantService.searchPlantByTag(dto.getText());
        List<PlantResponseDTO> resultDtos = new ArrayList<>();
        for(Plant p : list) {
            resultDtos.add(PlantResponseDTO.of(p));
        }
        return ResponseEntity.ok(resultDtos);
    }
}
