package org.alongtheblue.alongtheblue_server.domain.weather.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AddressSearchService {
    @Value("${security.oauth2.client.registration.kakao.client-id}")
    String apiKey;

    public String searchRegionCodeByAddress(String address) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK " + apiKey) // 발급받은 API 키
                .build();

        // WebClient 호출 및 Mono<String> 반환
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/address.json")
                        .queryParam("query", address)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(this::getRegionCodeFromResponse).block();
    }

    private Mono<String> getRegionCodeFromResponse(String jsonResponse) {
        try {
            // Jackson ObjectMapper를 사용하여 JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode documentsNode = rootNode.path("documents");

            if (documentsNode.isArray() && !documentsNode.isEmpty()) {
                JsonNode roadAddressNode = documentsNode.get(0).path("address");

                String code = roadAddressNode.path("h_code").asText();

                return Mono.just(code); // 행정구역을 Mono로 반환
            } else {
                System.out.println("해당 주소에 대한 검색 결과가 없습니다.");
                return Mono.empty(); // 결과가 없으면 빈 Mono 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Mono.error(e); // 예외 발생 시 Mono.error 반환
        }
    }
}
