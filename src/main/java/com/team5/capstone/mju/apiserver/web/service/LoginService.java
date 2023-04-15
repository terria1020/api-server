package com.team5.capstone.mju.apiserver.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.capstone.mju.apiserver.web.dto.LoginRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.LoginResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import com.team5.capstone.mju.apiserver.web.vo.NewOrFoundUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
public class LoginService {

    @Value("${extends.app-id}")
    private String appId;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidateToken(LoginRequestDto loginRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loginRequestDto.getAccessToken());

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        JsonNode node = null;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "https://kapi.kakao.com/v1/user/access_token_info",
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            );
            node = objectMapper.readTree(responseEntity.getBody());

            if (node.get("app_id").asText().equals(appId)) return true;
        } catch (HttpClientErrorException | JsonProcessingException e) { // json 해석 오류 / expired 되거나 잘못 된 token일 시(401)
            return false;
        }
        return false;
    }

    public Optional<JsonNode> getMyInfo(LoginRequestDto loginRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loginRequestDto.getAccessToken());

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        JsonNode node = null;

        try {
            node = objectMapper.readTree(responseEntity.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(node);
    }

    public NewOrFoundUser createOrFound(String kakaoAppUserId, String email, LocalDateTime dateJoined) { // 생성되면 true, 기존에 유저가 존재하면 false
        Optional<User> found = userRepository.findByKakaoAppUuid(kakaoAppUserId);

        if (found.isPresent()) return new NewOrFoundUser(found.get().getUserid(), false);
        User newUser = User.builder()
                .Userid(null)
                .name("").phone("").address("").carNumber("").socialLoginToken("")
                .email(email)
                .kakaoAppUuid(kakaoAppUserId)
                .dateJoined(dateJoined)
                .build();

        User savedUser = userRepository.save(newUser);
        return new NewOrFoundUser(savedUser.getUserid(), true);
    }

    public LoginResponseDto tryLogin(LoginRequestDto loginRequestDto) {
        if (!isValidateToken(loginRequestDto)) throw new RuntimeException();
        Optional<JsonNode> myInfo = getMyInfo(loginRequestDto);

        if (myInfo.isPresent()) {
            JsonNode node = myInfo.get();

            String kakaoAppUserId = node.get("id").asText();
            String email = node.get("kakao_account").get("email").asText();
            String connectedAt = node.get("connected_at").asText();

            LocalDateTime dateJoined = LocalDateTime.parse(connectedAt, DateTimeFormatter.ISO_DATE_TIME);

            NewOrFoundUser newOrFoundUser = createOrFound(kakaoAppUserId, email, dateJoined);
            return LoginResponseDto.builder()
                    .jwt(null)
                    .userId(newOrFoundUser.getUserId())
                    .isNewUser(newOrFoundUser.isNewUser())
                    .build();
        }
        throw new RuntimeException();
    }
}
