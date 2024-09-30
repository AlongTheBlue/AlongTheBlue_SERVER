package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
public class RestaurantService {
    private final restaurantRepository restaurantRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final RestaurantImageRepository restaurantImageRepository;
    private final String apiKey = "GY8BQwWZJD6QX3tfaQTpfYMRjcRnaHoPAxn/7u6ZffwScPHeO3TYZgA0zMPfnO/iSc/PunU/5rZYIa5jj98sUw==";
    private final String baseUrl = "http://apis.data.go.kr/B551011/KorService1";

    public RestaurantService(restaurantRepository restaurantRepository, WebClient.Builder webClientBuilder, ObjectMapper objectMapper, RestaurantImageRepository restaurantImageRepository) {
        this.restaurantRepository = restaurantRepository;
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
        this.restaurantImageRepository = restaurantImageRepository;
    }

    // API 호출 및 데이터 저장 로직
    public void fetchAndSaveData() {

        for (int i = 3; i < 4; i++) { // 1 and 11
            URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .path("/areaBasedList1")
                    .queryParam("serviceKey", apiKey)
                    .queryParam("numOfRows", 95) //95
                    .queryParam("pageNo", Integer.toString(i))
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "AppTest")
                    .queryParam("_type", "json")
                    .queryParam("listYN", "Y")
                    .queryParam("arrange", "A")
                    .queryParam("contentTypeId", 39)
                    .queryParam("areaCode", 39)
                    .queryParam("cat1", "A05")
                    .queryParam("cat2", "A0502")
                    .build()
                    .toUri();

            webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::parseJson)
                    .doOnNext(this::processItems)
                    .then()
                    .subscribe();
        }
    }

    private JsonNode parseJson(String response) {
        System.out.println(response);
        try {
            return objectMapper.readTree(response);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    private Restaurant parseRestaurant(JsonNode itemNode) {
        String cat3 = itemNode.path("cat3").asText();
        if (cat3.equals("A05020900")) return null;
        String contentId = itemNode.path("contentid").asText();
        String title = itemNode.path("title").asText();
        String addr = itemNode.path("addr1").asText();
        return new Restaurant(contentId, title, addr);
    }

    private void processItems(JsonNode rootNode) {
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
        if (itemsNode.isArray()) {
            Flux.fromIterable(itemsNode)
                    .flatMap(itemNode -> {
                        Restaurant restaurant = parseRestaurant(itemNode);
                        if (restaurant != null) {
                            return fetchIntroduction(restaurant)
                                    .flatMap(intro -> {
                                        restaurant.setIntroduction(intro);
                                        // 먼저 restaurant를 저장
                                        return restdayAndInfo(restaurant)
                                                .flatMap(restdayAndInfo -> {
                                                    restaurant.setRestDate(restdayAndInfo.get("restdate"));  // "resttime" -> "restdate"로 수정
                                                    restaurant.setInfoCenter(restdayAndInfo.get("infocenter"));  // "info" -> "infocenter"로 수정
                                                    restaurantRepository.save(restaurant);
                                                    return fetchAndSaveRestaurantImage(restaurant)  // 이미지 저장을 나중에 처리
                                                            .then(Mono.just(restaurant));
                                                });
                                    });
                        } else {
                            return Mono.empty();
                        }
                    })
                    .collectList()  // 모든 restaurant를 리스트로 수집
                    .subscribe();
        }
    }

    private Mono<String> fetchIntroduction(Restaurant restaurant) {

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/detailCommon1")
                .queryParam("serviceKey", apiKey)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("_type", "json")
                .queryParam("contentId", restaurant.getContentId())
                .queryParam("contentTypeId", "39")
                .queryParam("defaultYN", "Y")
                .queryParam("overviewYN", "Y")
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseJson)
                .map(rootNode -> rootNode.path("response").path("body").path("items").path("item").get(0).path("overview").asText());
    }

    private Mono<Void> fetchAndSaveRestaurantImage(Restaurant restaurant) {

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/detailImage1")
                .queryParam("serviceKey", apiKey)
                .queryParam("contentId", restaurant.getContentId())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("_type", "json")
                .queryParam("imageYN", "Y")
                .queryParam("subImageYN", "Y")
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseJson)
                .doOnNext(rootNode -> {
                    JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
                    if (itemsNode.isArray()) {
                        for (JsonNode itemNode : itemsNode) {
                            String imageUrl = itemNode.path("originimgurl").asText();
                            RestaurantImage restaurantImage = new RestaurantImage(restaurant, imageUrl);
                            restaurantImageRepository.save(restaurantImage);
                        }
                    }
                })
                .then();
    }

    private Mono<Map<String, String>> restdayAndInfo(Restaurant restaurant) {

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/detailIntro1")
                .queryParam("serviceKey", apiKey)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("_type", "json")
                .queryParam("contentId", restaurant.getContentId())
                .queryParam("contentTypeId", "39")
                .queryParam("numOfRows", "10")
                .queryParam("pageNo", "1")
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseJson)
                .map(rootNode -> {
                    JsonNode jsonNode = rootNode.path("response").path("body").path("items").path("item").get(0);
                    String restdate = jsonNode.path("restdatefood").asText();
                    String infocenter = jsonNode.path("infocenterfood").asText();

                    // 두 값을 Map에 담아서 반환
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("restdate", restdate);
                    resultMap.put("infocenter", infocenter);
                    return resultMap;
                });
    }

    public List<RestaurantDTO> getRestaurant() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDTO> dtos = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if(dtos.size() == 10) break;
            RestaurantDTO dto = new RestaurantDTO();
            List<RestaurantImage> imgs= restaurantImageRepository.findByrestaurant(restaurant);
            List<String> urls= new ArrayList<>();
            for(RestaurantImage resimg : imgs)  urls.add(resimg.getOriginimgurl());
            dto.setImgUrls(urls);
            dto.setAddress(restaurant.getAddr().substring(8));
            dto.setContentid(restaurant.getContentId());
            dto.setTitle(restaurant.getTitle());
            dto.setIntroduction(restaurant.getIntroduction());
            dto.setInfoCenter(restaurant.getInfoCenter());
            dto.setRestDate(restaurant.getRestDate());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<RestaurantDTO> homerestaurant() {
        Random random= new Random();
        Set<Integer> randomNumbers = new HashSet<>();
        List<RestaurantDTO> dtos= new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        Restaurant res;
        while (dtos.size() < 6) {
            int randomNumber = random.nextInt(17) ; // 저장된 restaurant 수로 할 것
            if (! randomNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber);
                res= restaurants.get(randomNumber);
                RestaurantDTO dto= new RestaurantDTO();
                dto.setTitle(res.getTitle());
                dto.setContentid(res.getContentId());
                String addr= res.getAddr().substring(8).split(" ")[0]
                        +" "+res.getAddr().substring(8).split(" ")[1];
                dto.setAddress(addr);
                List<RestaurantImage> imgs= restaurantImageRepository.findByrestaurant(res);
                if(!imgs.isEmpty()) {
                    dto.setImg(imgs.get(0).getOriginimgurl());
                    dtos.add(dto);}
            }
        }
        System.out.println(dtos.size());
        return dtos;
    }

    public void saveRestaurants() {
        for (int i = 1; i < 2; i++) { // 1 and 11
            URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .path("/areaBasedList1")
                    .queryParam("serviceKey", apiKey)
                    .queryParam("numOfRows", 95) //95
                    .queryParam("pageNo", Integer.toString(i))
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "AppTest")
                    .queryParam("_type", "json")
                    .queryParam("listYN", "Y")
                    .queryParam("arrange", "A")
                    .queryParam("contentTypeId", 39)
                    .queryParam("areaCode", 39)
                    .queryParam("cat1", "A05")
                    .queryParam("cat2", "A0502")
                    .build()
                    .toUri();

            webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::parseJson)
                    .doOnNext(this::processRestaurant)
                    .then()
                    .subscribe();
        }
    }

    private void processRestaurant(JsonNode rootNode) {
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
        if (itemsNode.isArray()) {
            for (JsonNode itemNode : itemsNode) {
                Restaurant restaurant = parseRestaurant(itemNode);
                if (restaurant != null) {
                    restaurantRepository.save(restaurant);  // 예시로 saveRestaurant 메서드를 통해 저장
                }
            }
        }
    }

    public void processSaveIntroduction() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            String introduction = fetchIntroduction(restaurant).block();
            restaurant.setIntroduction(introduction);
            restaurantRepository.save(restaurant);
        }
    }

    public void processSaveInfo() {
        List<Restaurant> restaurants = restaurantRepository.findAll();  // 모든 레스토랑을 불러옴

        for (Restaurant restaurant : restaurants) {
            String jsonResponse = getInfo(restaurant).block();

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
            JSONObject items = responseBody.getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");
            if (!itemArray.isEmpty()) {
                JSONObject item = itemArray.getJSONObject(0);
                String restdate = item.getString("restdatefood");
                String infocenter = item.getString("infocenterfood");
                restaurant.setRestDate(restdate);
                restaurant.setInfoCenter(infocenter);
                restaurantRepository.save(restaurant);
            }
        }
    }

    private Mono<String> getInfo(Restaurant restaurant){
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/detailIntro1")
                .queryParam("serviceKey", apiKey)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("_type", "json")
                .queryParam("contentId", restaurant.getContentId())
                .queryParam("contentTypeId", "39")
                .queryParam("numOfRows", "10")
                .queryParam("pageNo", "1")
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }

    public void processSaveImage() {
        List<Restaurant> restaurants = restaurantRepository.findAll();  // 모든 레스토랑을 불러옴
        for (Restaurant restaurant : restaurants) {
            fetchAndSaveRestaurantImage(restaurant).block();
        }
    }
}