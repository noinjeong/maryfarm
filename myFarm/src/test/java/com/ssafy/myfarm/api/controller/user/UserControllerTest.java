package com.ssafy.myfarm.api.controller.user;

import com.ssafy.myfarm.api.dto.user.request.CreateRequestDTO;
import com.ssafy.myfarm.api.dto.user.request.LoginRequestDTO;
import com.ssafy.myfarm.api.dto.user.response.UserResponseDTO;
import com.ssafy.myfarm.domain.user.User;
import com.ssafy.myfarm.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback(value = false)
class UserControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @AfterEach
    public void clean() {
//        userRepository.deleteAll();
    }
    @Test
    @DisplayName("saveUser")
    void saveUser() throws Exception {
        //given
        CreateRequestDTO dto = CreateRequestDTO.of("baek@ssafy.com", "1234", "seungbum", "19961111");
        String url = "http://localhost:"+port+"/api/auth/user/signup";
        //when
        ResponseEntity<UserResponseDTO> response = restTemplate.postForEntity(url, dto, UserResponseDTO.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<User> list = userRepository.findAll();
        assertThat(list.get(0).getEmail()).isEqualTo(dto.getEmail());
    }

    @Test
    @DisplayName("loginUser")
    void loginUser() throws Exception {
        //given
        LoginRequestDTO dto = LoginRequestDTO.of("baek@ssafy.com", "1234");
        String url = "http://localhost:"+port+"/api/auth/user/signin";
        //when
        ResponseEntity<UserResponseDTO> response = restTemplate.postForEntity(url, dto, UserResponseDTO.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo(dto.getEmail());
    }

}