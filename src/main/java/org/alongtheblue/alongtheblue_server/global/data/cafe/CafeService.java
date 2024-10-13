package org.alongtheblue.alongtheblue_server.global.data.cafe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.cafe.dto.PartCafeResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.global.dto.response.DetailResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.global.dto.response.HomeResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.weather.WeatherResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.weather.WeatherService;
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
public class CafeService {
    private final CafeRepository cafeRepository;
    private final CafeImageRepository cafeImageRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final WeatherService weatherService;
    private final OpenAIService openAIService;

    @Value("${api.key}")
    private String apiKey;

    private final String baseUrl = "http://apis.data.go.kr/B551011/KorService1";

    public CafeService(CafeRepository cafeRepository, CafeImageRepository cafeImageRepository, WebClient.Builder webClientBuilder, ObjectMapper objectMapper, WeatherService weatherService, OpenAIService openAIService) {
        this.cafeRepository = cafeRepository;
        this.cafeImageRepository = cafeImageRepository;
        this.webClient = webClientBuilder.baseUrl("http://apis.data.go.kr/B551011/KorService1").build();
        this.objectMapper = objectMapper;
        this.weatherService = weatherService;
        this.openAIService = openAIService;
    }

    //빌더는 baseUrl을 설정하여 기본적으로 사용할 API의 URL을 지정하는 거임

    // API 호출 및 데이터 저장 로직
    public Mono<Void> fetchAndSaveData() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/areaBasedList1")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("numOfRows", 10) //296
                        .queryParam("pageNo", 1)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "AppTest")
                        .queryParam("_type", "json")
                        .queryParam("listYN", "Y")
                        .queryParam("arrange", "A")
                        .queryParam("contentTypeId", 39)
                        .queryParam("areaCode", 39)
                        .queryParam("cat1", "A05")
                        .queryParam("cat2", "A0502")
                        .queryParam("cat3","A05020900")
                        .build())
                .retrieve()
                .bodyToMono(String.class) // JSON 응답을 문자열로 받음
                .map(this::parseJson) // JSON 문자열을 JsonNode로 변환
                .doOnNext(this::processItems) // 아이템 처리
                .then();
    }

    private JsonNode parseJson(String response) {
        try {
            return objectMapper.readTree(response);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
    private Mono<String> fetchIntroduction(Cafe cafe) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/detailCommon1")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "AppTest")
                        .queryParam("_type", "json")
                        .queryParam("contentTypeId", 39)
                        .queryParam("contentId", cafe.getContentId())
                        .queryParam("defaultYN", "Y")
                        .queryParam("overviewYN", "Y")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseJson)
                .map(rootNode -> rootNode.path("response").path("body").path("items").path("item").get(0).path("overview").asText());
    }

    private Mono<Map<String, String>> restdayAndInfo(Cafe cafe) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/detailIntro1")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "AppTest")
                        .queryParam("_type", "json")
                        .queryParam("contentId", cafe.getContentId())
                        .queryParam("contentTypeId", "39")
                        .queryParam("numOfRows", "10")
                        .queryParam("pageNo", "1")
                        .build())
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

    private Mono<Void> fetchAndSaveCafeImage(Cafe cafe) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/detailImage1")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("contentId", cafe.getContentId())
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "AppTest")
                        .queryParam("_type", "json")
                        .queryParam("imageYN", "Y")
                        .queryParam("subImageYN", "Y")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseJson)
                .doOnNext(rootNode -> {
                    JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
                    if (itemsNode.isArray()) {
                        for (JsonNode itemNode : itemsNode) {
                            String imageUrl = itemNode.path("originimgurl").asText();
                            CafeImage cafeImage = new CafeImage(cafe, imageUrl);
                            cafeImageRepository.save(cafeImage);
                        }
                    }
                })
                .then();
    }

    private void processItems(JsonNode rootNode) {
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
        if (itemsNode.isArray()) {
            Flux.fromIterable(itemsNode)
                    .flatMap(itemNode -> {
                        Cafe cafe = parseCafe(itemNode);
                        if (cafe != null) {
                            return fetchIntroduction(cafe)
                                    .flatMap(intro -> {
                                        cafe.setIntroduction(intro);
                                        // 먼저 restaurant를 저장
                                        return restdayAndInfo(cafe)
                                                .flatMap(restdayAndInfo -> {
                                                    cafe.setRestDate(restdayAndInfo.get("restdate"));  // "resttime" -> "restdate"로 수정
                                                    cafe.setInfoCenter(restdayAndInfo.get("infocenter"));  // "info" -> "infocenter"로 수정
                                                    cafeRepository.save(cafe);
                                                    return fetchAndSaveCafeImage(cafe)  // 이미지 저장을 나중에 처리
                                                            .then(Mono.just(cafe));
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


    private Cafe parseCafe(JsonNode itemNode) {
        String contentId = itemNode.path("contentid").asText();
        String title = itemNode.path("title").asText();
        String addr = itemNode.path("addr1").asText();
        String x = itemNode.path("mapx").asText();
        String y = itemNode.path("mapy").asText();
        return new Cafe(contentId, title, addr, x, y);
    }

    public List<CafeDTO> getAll() {
        List<Cafe> cafes= cafeRepository.findAll();
        CafeDTO dto= new CafeDTO();
        List<CafeDTO> dtos= new ArrayList<>();
        for(Cafe cafe: cafes){
            List<CafeImage> imgs= cafeImageRepository.findBycafe(cafe);
            List<String> urls= new ArrayList<>();
            for(CafeImage img : imgs)  urls.add(img.getOriginimgurl());
            dto.setAddress(cafe.getAddr().substring(8));
            dto.setContentid(cafe.getContentId());
            dto.setTitle(cafe.getTitle());
            dto.setImgUrls(urls);
            dtos.add(dto);
            dto.setIntroduction(cafe.getIntroduction());
            dto.setInfoCenter(cafe.getInfoCenter());
            dto.setRestDate(cafe.getRestDate());
        }
        return dtos;
    }

    public List<CafeDTO> homeCafe() {
        Random random= new Random();
        Set<Integer> randomNumbers = new HashSet<>();
        List<CafeDTO> dtos= new ArrayList<>();
        List<Cafe> cafes = cafeRepository.findAll();
        Cafe cafe;
        while (dtos.size() < 6) {
            int randomNumber = random.nextInt(10) ; // 저장된 restaurant 수로 할 것
            if (! randomNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber);
                CafeDTO dto= new CafeDTO();
                cafe= cafes.get(randomNumber);
                dto.setTitle(cafe.getTitle());
                dto.setContentid(cafe.getContentId());
                String addr= cafe.getAddr().substring(8).split(" ")[0]
                        +" "+cafe.getAddr().substring(8).split(" ")[1];
                dto.setAddress(addr);
                List<CafeImage> imgs= cafeImageRepository.findBycafe(cafe);

                if(! imgs.isEmpty())  {
                    dto.setImg(imgs.get(0).getOriginimgurl());
                    dtos.add(dto);
                }
            }
        }

        return dtos;
    }

    public CafeDTO getCafe(Long id) {
        Optional<Cafe> temp= cafeRepository.findById(id);
        if (temp.isPresent()){
            Cafe cafe= temp.get();
            CafeDTO dto= new CafeDTO();
            List<CafeImage> imgs= cafeImageRepository.findBycafe(cafe);
            List<String> urls= new ArrayList<>();
            for(CafeImage resimg : imgs)  urls.add(resimg.getOriginimgurl());
            dto.setImgUrls(urls);
            dto.setAddress(cafe.getAddr());
            dto.setContentid(cafe.getContentId());
            dto.setTitle(cafe.getTitle());
            dto.setIntroduction(cafe.getIntroduction());
            dto.setInfoCenter(cafe.getInfoCenter());
            dto.setRestDate(cafe.getRestDate());
            return dto;
        }
        else return null;
    }
  
    public void saveCafes() {
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
                    .queryParam("cat3","A05020900")
                    .build()
                    .toUri();

            webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::parseJson)
                    .doOnNext(this::processCafe)
                    .then()
                    .subscribe();
        }
    }

    private void processCafe(JsonNode rootNode) {
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
        if (itemsNode.isArray()) {
            for (JsonNode itemNode : itemsNode) {
                Cafe cafe = parseCafe(itemNode);
                if (cafe != null) {
                    cafeRepository.save(cafe);  // 예시로 saveRestaurant 메서드를 통해 저장
                }
            }
        }
    }

    public void processSaveIntroduction() {
        List<Cafe> cafes = cafeRepository.findAll();
        for (Cafe cafe : cafes) {
            String introduction = fetchIntroduction(cafe).block();
            cafe.setIntroduction(introduction);
            cafeRepository.save(cafe);
        }
    }

    public void processSaveInfo() {
        List<Cafe> cafes = cafeRepository.findAll();  // 모든 레스토랑을 불러옴

        for (Cafe cafe : cafes) {
            String jsonResponse = getInfo(cafe).block();

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
            JSONObject items = responseBody.getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");
            if (!itemArray.isEmpty()) {
                JSONObject item = itemArray.getJSONObject(0);
                String restdate = item.getString("restdatefood");
                String infocenter = item.getString("infocenterfood");
                cafe.setRestDate(restdate);
                cafe.setInfoCenter(infocenter);
                cafeRepository.save(cafe);
            }
        }
    }

    private Mono<String> getInfo(Cafe cafe){
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/detailIntro1")
                .queryParam("serviceKey", apiKey)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("_type", "json")
                .queryParam("contentId", cafe.getContentId())
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
        List<Cafe> cafes = cafeRepository.findAll();  // 모든 레스토랑을 불러옴
        for (Cafe cafe : cafes) {
            fetchAndSaveCafeImage(cafe).block();
        }
    }

    public ApiResponse<List<PartCafeResponseDto>> getCafesHome() {
        Random random= new Random();
        Set<Integer> randomNumbers = new HashSet<>();
        List<PartCafeResponseDto> dtos= new ArrayList<>();
//        List<Restaurant> restaurants = restaurantRepository.findAll();
        Long totalCount = cafeRepository.count();
        while (dtos.size() < 6) {
            int randomNumber = random.nextInt(totalCount.intValue()); // 저장된 restaurant 수로 할 것
            if (randomNumbers.contains(randomNumber)) {
                continue;
            }
            randomNumbers.add(randomNumber);
            Optional<Cafe> optionalCafe = cafeRepository.findById(Long.valueOf(randomNumber));
            if(optionalCafe.isEmpty()) {
                continue;
            }
            Cafe cafe = optionalCafe.get();
            String[] arr = cafe.getAddr().substring(8).split(" ");
//                    restaurant.setAddr(arr[0] + " " + arr[1]);
            PartCafeResponseDto responseDto = new PartCafeResponseDto(
                    arr[0] + " " + arr[1],
                    cafe.getTitle(),
                    cafe.getContentId(),
                    cafe.getCafeImages().isEmpty() ? null : cafe.getCafeImages().get(0).getOriginimgurl(),
                    cafe.getXMap(),
                    cafe.getYMap(),
                    "cafe"
            );
            dtos.add(responseDto);
        }
        System.out.println(dtos.size());
        return ApiResponse.ok("카페 정보를 성공적으로 조회했습니다.", dtos);
    }

    public ApiResponse<List<PartCafeResponseDto>> getCafesByKeyword(String keyword) {
        List<Cafe> optionalCafes = cafeRepository.findByTitleContaining(keyword);
        List<PartCafeResponseDto> partCafeResponseDtoList = new ArrayList<>();
        for(Cafe cafe: optionalCafes) {
            String[] arr = cafe.getAddr().substring(8).split(" ");
            PartCafeResponseDto partCafeResponseDto = new PartCafeResponseDto(
                    arr[0] + " " + arr[1],
                    cafe.getTitle(),
                    cafe.getContentId(),
                    cafe.getCafeImages().isEmpty() ? null : cafe.getCafeImages().get(0).getOriginimgurl(),
                    cafe.getXMap(),
                    cafe.getYMap(),
                    "cafe"
            );
            partCafeResponseDtoList.add(partCafeResponseDto);
        }
        return ApiResponse.ok("카페 정보를 성공적으로 검색했습니다.", partCafeResponseDtoList);
    }

    public ApiResponse<List<HomeResponseDto>> getHomeCafeList() {
        long totalCount = cafeRepository.count();
        Random random = new Random();
        List<HomeResponseDto> homeResponseDtoList = new ArrayList<>();

        // 2개의 이미지를 가진 레코드를 모을 때까지 반복
        while (homeResponseDtoList.size() < 2) {
            int randomOffset = random.nextInt((int) totalCount - 2); // 총 레코드 수에서 2개를 제외한 범위 내에서 랜덤 시작점 선택
            Pageable pageable = PageRequest.of(randomOffset, 2); // 한 번에 2개의 레코드 가져오기
            Page<Cafe> cafePage = cafeRepository.findAll(pageable); // Page 객체로 받음

            // 이미지를 가진 레코드만 필터링하여 DTO로 변환
            List<HomeResponseDto> filteredList = cafePage.getContent().stream()
                    .filter(cafe -> !cafe.getCafeImages().isEmpty()) // 이미지를 가진 레코드만 필터링
                    .map(cafe -> {
                        String[] arr = cafe.getAddr().substring(8).split(" ");
                        return new HomeResponseDto(
                                cafe.getContentId(),
                                cafe.getTitle(),
                                arr[0] + " " + arr[1],
                                cafe.getCafeImages().get(0).getOriginimgurl() // 첫 번째 이미지 가져오기
                        );
                    })
                    .toList();

            homeResponseDtoList.addAll(filteredList);
            homeResponseDtoList = homeResponseDtoList.stream().distinct().limit(2).collect(Collectors.toList());
        }
        return ApiResponse.ok("이미지를 포함한 카페 정보를 성공적으로 조회했습니다.", homeResponseDtoList);
    }


    public ApiResponse<DetailResponseDto> getCafeDetail(String id) {
        Cafe cafe = findByContentId(id);
        WeatherResponseDto weather = weatherService.getWeatherByAddress(cafe.getAddr());

        DetailResponseDto detailResponseDto = new DetailResponseDto(
                cafe.getContentId(),
                cafe.getTitle(),
                cafe.getAddr(),
                cafe.getRestDate(),
                weather.weatherCondition(),
                weather.temperature(),
                cafe.getInfoCenter(),
                cafe.getIntroduction(),
                cafe.getCafeImages().get(0).getOriginimgurl(),
                cafe.getXMap(),
                cafe.getYMap()
        );
        return ApiResponse.ok("해당 카페의 상세 정보를 조회하였습니다", detailResponseDto);
    }

    public Cafe findByContentId(String  id) {
        Optional<Cafe> optionalCafe = cafeRepository.findByContentId(id);
        if(optionalCafe.isEmpty())
            throw new RuntimeException("존재하지 않는 카페 ID");
        else
            return optionalCafe.get();
    }

    public ApiResponse<List<String>> getHashtagsById(String id) {
        Cafe cafe = findByContentId(id);
        List<String> hashtags = openAIService.getHashtags(cafe.getIntroduction());
        return ApiResponse.ok(hashtags);
    }
}
