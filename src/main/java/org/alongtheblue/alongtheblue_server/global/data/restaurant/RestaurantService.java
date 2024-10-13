package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.global.dto.response.DetailResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.global.dto.response.HomeResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.dto.response.PartRestaurantResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.weather.WeatherResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.weather.WeatherService;
import org.alongtheblue.alongtheblue_server.global.error.ErrorCode;
import org.alongtheblue.alongtheblue_server.global.gpt.OpenAIService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final RestaurantImageRepository restaurantImageRepository;
    private final WeatherService weatherService;
    private final OpenAIService openAIService;

    @Value("${api.key}")
    private String apiKey;
    private final String baseUrl = "http://apis.data.go.kr/B551011/KorService1";

    public RestaurantService(RestaurantRepository restaurantRepository, WebClient.Builder webClientBuilder, ObjectMapper objectMapper, RestaurantImageRepository restaurantImageRepository, WeatherService  weatherService, OpenAIService openAIService) {
        this.restaurantRepository = restaurantRepository;
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
        this.restaurantImageRepository = restaurantImageRepository;
        this.weatherService = weatherService;
        this.openAIService = openAIService;
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
        String x = itemNode.path("mapx").asText();
        String y = itemNode.path("mapy").asText();
        return new Restaurant(contentId, title, addr, x, y);
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

//    //딱 음식 눌렀을 때 식당 목록
    public ApiResponse<Page<RestaurantSimpleInformation>> retrieveAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 1. Restaurant 기준으로 페이징 처리된 데이터를 조회
        Page<RestaurantSimpleInformation> restaurantPage = restaurantRepository.findAllSimple(pageable);

        // 2. RestaurantSimpleInformation으로 변환하여 이미지 그룹화
//        List<RestaurantSimpleInformation> groupedRestaurantList = restaurantPage.getContent().stream()
//                .map(restaurant -> new RestaurantSimpleInformationImpl(
//                        restaurant.getContentId(),
//                        restaurant.getTitle(),
//                        restaurant.getAddress(),
//                        restaurant.getImages()  // 이미지를 그룹화하지 않고 그대로 넣음
//                ))
//                .collect(Collectors.toList());
//
//        // 3. Restaurant 기준으로 페이징을 다시 적용하여 반환
//        Page<RestaurantSimpleInformation> pagedResult = new PageImpl<>(
//                groupedRestaurantList, pageable, restaurantPage.getTotalElements());

        // 4. 결과를 ApiResponse로 반환
        return ApiResponse.ok("음식점 목록을 성공적으로 조회했습니다.", restaurantPage);
    }
//    public List<RestaurantResponseDto> getAll() {
//        List<Restaurant> restaurants = restaurantRepository.findAll();
//        List<RestaurantResponseDto> dtos = new ArrayList<>();
//        for (Restaurant restaurant : restaurants) {
//            if(dtos.size() == 10) break;
//            RestaurantResponseDto dto = new RestaurantResponseDto();
//            List<RestaurantImage> imgs= restaurantImageRepository.findByrestaurant(restaurant);
//            List<String> urls= new ArrayList<>();
//            for(RestaurantImage resimg : imgs)  urls.add(resimg.getOriginimgurl());
////            dto.setImgUrls(urls);
////            dto.setAddress(restaurant.getAddr());
////            dto.setContentid(restaurant.getContentId());
////            dto.setTitle(restaurant.getTitle());
//            dtos.add(dto);
//        }
//        return dtos;
//    }

    public ApiResponse<List<PartRestaurantResponseDto>> homerestaurant() {
        Random random= new Random();
        Set<Integer> randomNumbers = new HashSet<>();
        List<PartRestaurantResponseDto> dtos= new ArrayList<>();
//        List<Restaurant> restaurants = restaurantRepository.findAll();
        Long totalCount = restaurantRepository.count();
        while (dtos.size() < 6) {
            int randomNumber = random.nextInt(totalCount.intValue()); // 저장된 restaurant 수로 할 것
            if (randomNumbers.contains(randomNumber)) {
                continue;
            }
            randomNumbers.add(randomNumber);
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(Long.valueOf(randomNumber));
            if(optionalRestaurant.isEmpty()) {
                continue;
            }
            Restaurant restaurant = optionalRestaurant.get();
            String[] arr = restaurant.getAddress().substring(8).split(" ");
//                    restaurant.setAddr(arr[0] + " " + arr[1]);
            PartRestaurantResponseDto responseDto = new PartRestaurantResponseDto(
                    arr[0] + " " + arr[1],
                    restaurant.getTitle(),
                    restaurant.getContentId(),
                    restaurant.getImages().isEmpty() ? null : restaurant.getImages().get(0).getOriginimgurl(),
                    restaurant.getXMap(),
                    restaurant.getYMap(),
                    "restaurant"
            );
            dtos.add(responseDto);
        }
        System.out.println(dtos.size());
        return ApiResponse.ok("음식점 정보를 성공적으로 조회했습니다.", dtos);
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
    //상세보기
    public ApiResponse<Restaurant> getRestaurant(Long id) {
        Optional<Restaurant> optionalRestaurant= restaurantRepository.findById(id);
        if(optionalRestaurant.isEmpty()) {
            return ApiResponse.withError(ErrorCode.INVALID_RESTAURANT_ID);
        }
        Restaurant restaurant= optionalRestaurant.get();
        restaurant.setAddress(restaurant.getAddress().substring(8));
        return ApiResponse.ok("음식점 정보를 성공적으로 조회했습니다.", restaurant);
//            RestaurantDTO dto= new RestaurantDTO();
//            List<RestaurantImage> imgs= restaurantImageRepository.findByrestaurant(restaurant);
//            List<String> urls= new ArrayList<>();
//            for(RestaurantImage resimg : imgs)  urls.add(resimg.getOriginimgurl());
//            dto.setImgUrls(urls);
//            dto.setAddress(restaurant.getAddr().substring(8));
//            dto.setContentid(restaurant.getContentId());
//            dto.setTitle(restaurant.getTitle());
//            dto.setIntroduction(restaurant.getIntroduction());
//            dto.setInfoCenter(restaurant.getInfoCenter());
//            dto.setRestDate(restaurant.getRestDate());
//            return dto;
//        }
//        else return null;
    }

    public ApiResponse<List<PartRestaurantResponseDto>> getRestaurantsByKeyword(String keyword) {
        List<Restaurant> optionalRestaurants = restaurantRepository.findByTitleContaining(keyword);
        List<PartRestaurantResponseDto> partRestaurantResponseDtoList = new ArrayList<>();
        for(Restaurant restaurant: optionalRestaurants) {
            String[] arr = restaurant.getAddress().substring(8).split(" ");
            PartRestaurantResponseDto restaurantResponseDto = new PartRestaurantResponseDto(
                    arr[0] + " " + arr[1],
                    restaurant.getTitle(),
                    restaurant.getContentId(),
                    restaurant.getImages().isEmpty() ? null : restaurant.getImages().get(0).getOriginimgurl(),
                    restaurant.getXMap(),
                    restaurant.getYMap(),
                    "restaurant"
            );
            partRestaurantResponseDtoList.add(restaurantResponseDto);
        }
        return ApiResponse.ok("음식점 정보를 성공적으로 검색했습니다.", partRestaurantResponseDtoList);
    }

    public ApiResponse<List<HomeResponseDto>> getHomeRestaurant() {
        long totalCount = restaurantRepository.count();
        Random random = new Random();
        List<HomeResponseDto> homeResponseDtoList = new ArrayList<>();

        // 2개의 이미지를 가진 레코드를 모을 때까지 반복
        while (homeResponseDtoList.size() < 2) {
            int randomOffset = random.nextInt((int) totalCount - 2); // 총 레코드 수에서 2개를 제외한 범위 내에서 랜덤 시작점 선택
            Pageable pageable = PageRequest.of(randomOffset, 2); // 한 번에 2개의 레코드 가져오기
            Page<Restaurant> restaurantPage = restaurantRepository.findAll(pageable); // Page 객체로 받음

            // 이미지를 가진 레코드만 필터링하여 DTO로 변환
            List<HomeResponseDto> filteredList = restaurantPage.getContent().stream()
                    .filter(restaurant -> !restaurant.getImages().isEmpty()) // 이미지를 가진 레코드만 필터링
                    .map(restaurant -> {
                        String[] arr = restaurant.getAddress().substring(8).split(" ");
                        return new HomeResponseDto(
                                restaurant.getContentId(),
                                restaurant.getTitle(),
                                arr[0] + " " + arr[1],
                                restaurant.getImages().get(0).getOriginimgurl() // 첫 번째 이미지 가져오기
                        );
                    })
                    .toList();

            homeResponseDtoList.addAll(filteredList);
            homeResponseDtoList = homeResponseDtoList.stream().distinct().limit(2).collect(Collectors.toList());
        }
        return ApiResponse.ok("이미지를 포함한 음식점 정보를 성공적으로 조회했습니다.", homeResponseDtoList);
    }

    public ApiResponse<DetailResponseDto> getRestaurantDetail(String id) {
        Restaurant restaurant = findByContentId(id);
        WeatherResponseDto weather = weatherService.getWeatherByAddress(restaurant.getAddress());
        DetailResponseDto detailResponseDto = new DetailResponseDto(
                restaurant.getContentId(),
                restaurant.getTitle(),
                restaurant.getAddress(),
                restaurant.getRestDate(),
                weather.weatherCondition(),
                weather.temperature(),
                restaurant.getInfoCenter(),
                restaurant.getIntroduction(),
                restaurant.getImages().get(0).getOriginimgurl(),
                restaurant.getXMap(),
                restaurant.getYMap()
        );
        return ApiResponse.ok("해당 음식점의 상세 정보를 조회하였습니다", detailResponseDto);
    }

    public Restaurant findByContentId(String id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByContentId(id);
        if(optionalRestaurant.isEmpty())
            throw new RuntimeException("해당 ID의 음식점이 없습니다.");
        else
            return optionalRestaurant.get();
    }

    public ApiResponse<List<String>> getHashtagsById(String id) {
        Restaurant restaurant = findByContentId(id);
        List<String> hashtags = openAIService.getHashtags(restaurant.getIntroduction());
        return ApiResponse.ok(hashtags);
    }
}