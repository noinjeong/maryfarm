package com.ssafy.maryfarmplantservice.api.controller.plant;

import com.ssafy.maryfarmplantservice.api.dto.diary.request.InitDiaryRequestDTO;
import com.ssafy.maryfarmplantservice.api.dto.plant.request.MonthPlantSearchRequestDTO;
import com.ssafy.maryfarmplantservice.api.dto.plant.response.PlantResponseDTO;
import com.ssafy.maryfarmplantservice.api.dto.plant.response.PlantSearchByMonthResposeDTO;
import com.ssafy.maryfarmplantservice.client.dto.user.UserResponseDTO;
import com.ssafy.maryfarmplantservice.client.service.user.UserServiceClient;
import com.ssafy.maryfarmplantservice.domain.diary.Diary;
import com.ssafy.maryfarmplantservice.domain.plant.Plant;
import com.ssafy.maryfarmplantservice.service.PlantService;
import com.ssafy.maryfarmplantservice.util.file.dto.FileDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PlantController {
    private final PlantService plantService;
    private final UserServiceClient userServiceClient;
    @Operation(summary = "특정 작물 조회", description = "특정 작물의 정보를 조회합니다.", tags = { "Plant Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/plant/{plantId}")
    public ResponseEntity<?> searchPlant(@PathVariable("plantId") String plantId) throws IOException {
        Plant plant = plantService.findPlant(plantId);
        UserResponseDTO userDto = userServiceClient.searchUser(plant.getUserId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Plant.class, PlantResponseDTO.class).addMappings(mapper -> {
            mapper.map(Plant::getId, PlantResponseDTO::setPlantId);
        });
        PlantResponseDTO dto = modelMapper.map(plant, PlantResponseDTO.class);
        dto.setUser(userDto);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "특정 년월의 작물들 조회", description = "특정 년월에 해당하는 자신의 작물들을 조회합니다.", tags = { "Plant Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MonthPlantSearchRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/plant/month/search")
    public ResponseEntity<?> searchPlant(@RequestBody MonthPlantSearchRequestDTO dto) throws IOException {
        List<Plant> list = plantService.searchPlantByMonth(dto.getUserId(),dto.getYear(),dto.getMonth());
        List<PlantSearchByMonthResposeDTO> resultDtos = new ArrayList<>();
        for(Plant p : list) {
            resultDtos.add(PlantSearchByMonthResposeDTO.of(p));
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "작물 재배 처리", description = "작물을 재배 처리합니다.", tags = { "Plant Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/plant/harvest/{plantId}")
    public ResponseEntity<?> doHarvest(@PathVariable("plantId") String plantId) throws IOException {
        plantService.doHarvest(plantId);
        return ResponseEntity.ok(1);
    }

}
