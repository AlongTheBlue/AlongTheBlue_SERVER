package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.util.*;

@Service
public class restaurantService {
    private final restaurantRepository restaurantRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private String apiKey = "GY8BQwWZJD6QX3tfaQTpfYMRjcRnaHoPAxn/7u6ZffwScPHeO3TYZgA0zMPfnO/iSc/PunU/5rZYIa5jj98sUw==";

    //빌더는 baseUrl을 설정하여 기본적으로 사용할 API의 URL을 지정하는 거임
    public restaurantService(restaurantRepository restaurantRepository, WebClient.Builder webClientBuilder, ObjectMapper objectMapper){
        this.restaurantRepository = restaurantRepository;
        this.webClient = webClientBuilder.baseUrl("http://apis.data.go.kr/B551011/KorService1").build();
        this.objectMapper = objectMapper;
    }

        // API 호출 및 데이터 저장 로직
        public Mono<Void> fetchAndSaveData() {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/areaBasedList1")
                            .queryParam("serviceKey", apiKey)
                            .queryParam("numOfRows", 10) // 페이지당 10개의 아이템
                            .queryParam("pageNo", 1) // 페이지 번호 1
                            .queryParam("MobileOS", "ETC")
                            .queryParam("MobileApp", "AppTest")
                            .queryParam("_type", "json")
                            .queryParam("listYN", "Y")
                            .queryParam("arrange", "A")
                            .queryParam("contentTypeId", 39)
                            .queryParam("areaCode", 39)
                            .queryParam("cat1", "A05")
                            .queryParam("cat2", "A0502")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class) // JSON 응답을 문자열로 받음
                    .map(this::parseJson) // JSON 문자열을 JsonNode로 변환
                    .doOnNext(this::processItems) // 아이템 처리
                    .then(); // Mono<Void> 반환
        }

    private JsonNode parseJson(String response) {
        try {
            return objectMapper.readTree(response);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    private void processItems(JsonNode rootNode) {
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
        List<Restaurant> restaurants = new ArrayList<>();

        if (itemsNode.isArray()) {
            Iterator<JsonNode> elements = itemsNode.elements();
            while (elements.hasNext()) {
                JsonNode itemNode = elements.next();
                Restaurant restaurant = parseRestaurant(itemNode);
                restaurants.add(restaurant);
            }
        }

        if (!restaurants.isEmpty()) {
            restaurantRepository.saveAll(restaurants);
        }
    }


    private Restaurant parseRestaurant(JsonNode itemNode) {
        String contentId = itemNode.path("contentid").asText();
        String title = itemNode.path("title").asText();
        String cat1 = itemNode.path("cat1").asText();
        String cat2 = itemNode.path("cat2").asText();
        String cat3 = itemNode.path("cat3").asText();
        String addr1 = itemNode.path("addr1").asText();
        String addr2 = itemNode.has("addr2") ? itemNode.path("addr2").asText() : null;
        String areaCode = itemNode.path("areacode").asText();

        return new Restaurant(contentId, title, cat1, cat2, cat3, addr1, addr2, areaCode);
    }
    }