package com.ssafy.myfarm.api.controller.plant;

import com.ssafy.myfarm.domain.plant.Plant;
import com.ssafy.myfarm.service.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PlantController {
    private final PlantService plantService;

    public ResponseEntity<?> searchPlant(@RequestBody String text) {
        List<Plant> list = plantService.searchPlantByTag(text);
        return new ResponseEntity<List<Plant>>(list, HttpStatus.OK);
    }
}
