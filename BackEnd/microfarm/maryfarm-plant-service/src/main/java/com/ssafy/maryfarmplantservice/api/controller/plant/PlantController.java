package com.ssafy.maryfarmplantservice.api.controller.plant;

import com.ssafy.maryfarmplantservice.service.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PlantController {
    private final PlantService plantService;
}
