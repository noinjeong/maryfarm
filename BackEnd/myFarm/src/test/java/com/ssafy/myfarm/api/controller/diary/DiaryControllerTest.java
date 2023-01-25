//package com.ssafy.myfarm.api.controller.diary;
//
//import com.google.gson.Gson;
//import com.ssafy.myfarm.InitDb;
//import com.ssafy.myfarm.api.dto.diary.request.DiaryLikeRequestDTO;
//import com.ssafy.myfarm.api.dto.diary.request.InitDiaryRequestDTO;
//import com.ssafy.myfarm.domain.diary.Diary;
//import com.ssafy.myfarm.domain.plant.Plant;
//import com.ssafy.myfarm.domain.user.Tier;
//import com.ssafy.myfarm.domain.user.User;
//import com.ssafy.myfarm.repository.DiaryRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.*;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import javax.transaction.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
//@Rollback(value = false)
//class DiaryControllerTest {
//    @LocalServerPort
//    private int port;
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Autowired
//    private DiaryRepository diaryRepository;
//    @AfterEach
//    public void clean() {
//        diaryRepository.deleteAll();
//    }
//    @BeforeEach
//    public void setUp() {
//
//    }
////    @Test
////    @DisplayName("saveDiary")
////    void saveDiary() throws Exception {
////        //given
////        Gson gson = new Gson();
////        InitDiaryRequestDTO dto = new InitDiaryRequestDTO("1111", "감자", "나의 감자일기", "감자를 처음 심었어요.");
////        String url = "http://localhost:"+port+"/api/diary/init";
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
////        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
////        body.add("image", null);
////        body.add("dto", gson.toJson(dto));
////        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(body, headers);
////        //when
//////        ResponseEntity<String> response = restTemplate.postForEntity(url, dto, String.class);
////        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
////        //then
////        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
////    }
//    @Test
//    @DisplayName("DiaryLike")
//    void diaryLike() throws Exception {
//        //given
//        Diary diary = diaryRepository.findDiaryByUserId("15151515").get(0);
//        DiaryLikeRequestDTO dto = new DiaryLikeRequestDTO(diary.getId(), "15151515");
//        String url = "http://localhost:"+port+"/api/diary/like";
//        //when
//        ResponseEntity<Object> response = restTemplate.postForEntity(url, dto, null);
//        //then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//
//}