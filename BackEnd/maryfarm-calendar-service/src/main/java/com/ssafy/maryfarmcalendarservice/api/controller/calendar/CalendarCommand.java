package com.ssafy.maryfarmcalendarservice.api.controller.calendar;

<<<<<<< HEAD
import com.ssafy.maryfarmcalendarservice.api.dto.calendar.request.RegistCalendarRequestDTO;
import com.ssafy.maryfarmcalendarservice.api.dto.calendar.request.SearchCalendarRequestDTO;
import com.ssafy.maryfarmcalendarservice.api.dto.calendar.response.SearchCalendarResponseDTO;
import com.ssafy.maryfarmcalendarservice.client.dto.plant.PlantResponseDTO;
=======
import com.ssafy.maryfarmcalendarservice.api.dto.calendar.RegistCalendarRequestDTO;
>>>>>>> 3413e9b185e651b91a370b6adee82037c2fd68a8
import com.ssafy.maryfarmcalendarservice.client.service.plant.PlantServiceClient;
import com.ssafy.maryfarmcalendarservice.domain.calendar.Calendar;
import com.ssafy.maryfarmcalendarservice.kafka.producer.calendar.CalendarProducer;
import com.ssafy.maryfarmcalendarservice.service.CalendarCService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class CalendarCommand {
    private final CalendarCService calendarCService;
    private final CalendarProducer calendarProducer;
    private final PlantServiceClient plantServiceClient;

    @Operation(summary = "일별 달력 정보 등록", description = "특정 일의 작물 관리 상태를 등록합니다.", tags = { "Calendar Command" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
<<<<<<< HEAD
    @PostMapping("/calendar/search")
    public ResponseEntity<?> SearchCalendar(@RequestBody SearchCalendarRequestDTO dto) {
        List<Calendar> list = calendarService.searchCalendar(dto);
        List<SearchCalendarResponseDTO> resultDtos = new ArrayList<>();
        for(Calendar c : list) {
            PlantResponseDTO plantResponseDTO = plantServiceClient.searchPlant(c.getId().getPlantId());
            resultDtos.add(SearchCalendarResponseDTO.of(c,plantResponseDTO));
        }
        return ResponseEntity.ok(resultDtos);
    }

    @Operation(summary = "일별 달력 정보 등록", description = "특정 일의 작물 관리 상태를 등록합니다.", tags = { "Calendar Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
=======
>>>>>>> 3413e9b185e651b91a370b6adee82037c2fd68a8
    @PostMapping("/calendar/regist")
    public ResponseEntity<?> RegistCalendar(@RequestBody RegistCalendarRequestDTO dto) {
        Calendar calendar = calendarCService.registCalendar(dto);
        /*
            생성, 업데이트 둘다 save 메서드로 해결하므로 아직 Update처리는 못해줌.
         */
        return ResponseEntity.ok(1);
    }
}
