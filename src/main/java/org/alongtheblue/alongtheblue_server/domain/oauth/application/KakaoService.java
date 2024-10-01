package org.alongtheblue.alongtheblue_server.domain.oauth.application;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoService {

    @Value("${security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${security.oauth2.client.provider.kakao.authorization-uri}")
    private String authUrl;

    @Value("${security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Value("${security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    private final WebClient webClient;

    @Autowired
    public KakaoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://kauth.kakao.com").build();
    }

    public ApiResponse<String> getKakaoLoginUrl(){
        String kakaoAuthUrl = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code",
                authUrl, clientId, redirectUri);
        return ApiResponse.ok("Redirect to Kakao", kakaoAuthUrl);
    }

    public String getAccessToken(String code) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"))
                .block(); // 비동기 처리 시 block() 사용 자제, 여기서는 예제 단순화를 위해 사용
    }

    public Map<String, Object> getUserInfo(String accessToken) {
        return webClient.mutate()
                .baseUrl("https://kapi.kakao.com")
                .build()
                .get()
                .uri("/v2/user/me")
//                .uri("https://kapi.kakao.com/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block(); // 비동기 처리 시 block() 사용 자제, 여기서는 예제 단순화를 위해 사용
    }
}
