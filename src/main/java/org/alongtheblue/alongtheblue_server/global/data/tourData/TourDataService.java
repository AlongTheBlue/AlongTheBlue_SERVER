package org.alongtheblue.alongtheblue_server.global.data.tourData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.global.Category;
import org.alongtheblue.alongtheblue_server.global.data.global.CustomPage;
import org.alongtheblue.alongtheblue_server.global.data.global.SimpleInformation;
import org.alongtheblue.alongtheblue_server.global.data.global.dto.response.DetailResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.global.dto.response.HomeResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.tourData.dto.TourDataResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.weather.WeatherResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.weather.WeatherService;
import org.alongtheblue.alongtheblue_server.global.gpt.OpenAIService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourDataService {

    private final TourDataRepository tourDataRepository;
    private final TourDataImageRepository tourDataImageRepository;
    private final WeatherService weatherService;
    private final OpenAIService openAIService;

    @Value("${api.key}")
    private String apiKey;

//    public ArrayList<TourData> getTourData() {
//        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
//        ArrayList<TourData> tourDataList = new ArrayList<>();
//        WebClient webClient = WebClient.builder().baseUrl(url).build();
//
//        // 먼저 totalCount 값을 가져옴
//        int totalCount = getTotalCount(webClient);
//        int numOfRows = 10;  // 한 페이지에 가져올 데이터 수
//        int totalPages = (int) Math.ceil((double) totalCount / numOfRows);  // 총 페이지 수 계산
//
//        // 페이지별로 데이터를 가져오는 반복문
//        for (int pageNo = 1; pageNo <= 10; pageNo++) {
//            int finalPageNo = pageNo;
//            Mono<String> response = webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//                            .queryParam("serviceKey", apiKey)
//                            .queryParam("numOfRows", numOfRows)
//                            .queryParam("pageNo", finalPageNo)  // 페이지 번호 변경
//                            .queryParam("MobileOS", "ETC")
//                            .queryParam("MobileApp", "AppTest")
//                            .queryParam("_type", "json")
//                            .queryParam("listYN", "Y")
//                            .queryParam("arrange", "A")
//                            .queryParam("contentTypeId", 12)
//                            .queryParam("areaCode", 39)
//                            .build())
//                    .accept(MediaType.APPLICATION_JSON)
//                    .retrieve()
//                    .bodyToMono(String.class);
//
//            String jsonResponse = response.block();
//
//            // JSON 데이터 파싱
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//            JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
//            JSONObject items = responseBody.getJSONObject("items");
//            JSONArray itemArray = items.getJSONArray("item");
//
//            // 관광지 정보 출력 및 리스트에 추가
//            for (int i = 0; i < itemArray.length(); i++) {
//                JSONObject item = itemArray.getJSONObject(i);
//                String id = item.getString("contentid");
//                String title = item.getString("title");
//                String address = item.getString("addr1");
//                String x = item.getString("mapx");
//                String y = item.getString("mapy");
//
//                TourData tourData = TourData.builder()
//                        .contentId(id)
//                        .title(title)
//                        .address(address)
//                        .xMap(x)
//                        .yMap(y)
//                        .build();
//                tourDataList.add(tourData);
//                tourDataRepository.save(tourData);
//            }
//            System.out.println(pageNo);
//
//        }
//        return tourDataList;
//    }

    public ArrayList<TourData> getTourData() {
        String baseUrl = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
        ArrayList<TourData> tourDataList = new ArrayList<>();
        int numOfRows = 10;  // 한 페이지에 가져올 데이터 수

        // WebClient 생성
        WebClient webClient = WebClient.builder().build();

        // 페이지별로 데이터를 가져오는 반복문
        for (int pageNo = 1; pageNo <= 10; pageNo++) {
            // URI 객체를 UriComponentsBuilder로 구성
            URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("serviceKey", apiKey)
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("pageNo", pageNo)  // 페이지 번호 변경
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "AppTest")
                    .queryParam("_type", "json")
                    .queryParam("listYN", "Y")
                    .queryParam("arrange", "A")
                    .queryParam("contentTypeId", 12)
                    .queryParam("areaCode", 39)
                    .build()
                    .toUri();  // URI 객체로 변환

            Mono<String> response = webClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class);

            String jsonResponse = response.block();

            // JSON 데이터 파싱
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
            JSONObject items = responseBody.getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");

            // 관광지 정보 출력 및 리스트에 추가
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject item = itemArray.getJSONObject(i);
                String id = item.getString("contentid");
                String title = item.getString("title");
                String address = item.getString("addr1");
                String x = item.getString("mapx");
                String y = item.getString("mapy");

                TourData tourData = TourData.builder()
                        .contentId(id)
                        .title(title)
                        .address(address)
                        .xMap(x)
                        .yMap(y)
                        .build();
                tourDataList.add(tourData);
                tourDataRepository.save(tourData);
            }
            System.out.println(pageNo);
        }
        return tourDataList;
    }

    public int getTotalCount(WebClient webClient) {
        // 총 데이터 수를 가져오는 API 요청
        Mono<String> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", apiKey)
                        .queryParam("numOfRows", 1)  // 데이터는 1개만 요청
                        .queryParam("pageNo", 1)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "AppTest")
                        .queryParam("_type", "json")
                        .queryParam("listYN", "Y")
                        .queryParam("arrange", "A")
                        .queryParam("contentTypeId", 12)
                        .queryParam("areaCode", 39)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
        int totalNum = response.map(jsonResponse -> {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
            return responseBody.getInt("totalCount");  // totalCount 반환
        }).block();
        // totalCount를 파싱
        return totalNum;  // 비동기 응답을 동기로 변환하여 값 반환
    }

    @Transactional
//    public List<TourData> updateOverviews() {
//        String url = "https://apis.data.go.kr/B551011/KorService1/detailCommon1";
//        // 1. 모든 TourData 엔티티 조회
//        List<TourData> tourDataList = tourDataRepository.findAll();
//        WebClient webClient = WebClient.builder().baseUrl(url).build();
//
//        // 2. 각 contentId로 API 호출 및 데이터 업데이트
//        // for (int i = 0; i < 10; i++) {
//        for (TourData tourData : tourDataList) {
//        //    TourData tourData = tourDataList.get(i);
//            String contentId = tourData.getContentId();
//            System.out.println(contentId);
//            Mono<String> response = webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//                            .queryParam("serviceKey", apiKey)
//                            .queryParam("MobileOS", "ETC")
//                            .queryParam("MobileApp", "AppTest")
//                            .queryParam("_type", "json")
//                            .queryParam("contentId", contentId)
//                            .queryParam("contentTypeId", 12)
//                            .queryParam("defaultYN", "Y")
//                            .queryParam("firstImageYN", "Y")
//                            .queryParam("areacodeYN", "Y")
//                            .queryParam("catcodeYN", "Y")
//                            .queryParam("addrinfoYN", "Y")
//                            .queryParam("mapinfoYN", "Y")
//                            .queryParam("overviewYN", "Y")
//                            .queryParam("numOfRows", 1)
//                            .queryParam("pageNo", 1)
//                            .build())
//                    .retrieve()
//                    .bodyToMono(String.class);
//
//            String jsonResponse = response.block();
//            System.out.println("API Response: " + jsonResponse);
//            // JSON 데이터 파싱하여 overview 가져오기
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//            JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
//            JSONObject items = responseBody.getJSONObject("items");
//            JSONArray itemArray = items.getJSONArray("item");
//            if (!itemArray.isEmpty()) {
//                JSONObject item = itemArray.getJSONObject(0);
//                String overview = item.getString("overview");
//
//                // 3. `overview` 데이터 업데이트
//                tourData.setIntroduction(overview);
//                tourDataRepository.save(tourData);  // JPA를 통해 저장
//            }
//        }
//        return tourDataRepository.findAll();
//    }

    public List<TourData> updateOverviews() {
        String baseUrl = "https://apis.data.go.kr/B551011/KorService1/detailCommon1";
        // 1. 모든 TourData 엔티티 조회
        List<TourData> tourDataList = tourDataRepository.findAll();
        WebClient webClient = WebClient.builder().build();

        // 2. 각 contentId로 API 호출 및 데이터 업데이트
        for (TourData tourData : tourDataList) {
            String contentId = tourData.getContentId();
            System.out.println(contentId);

            // URI 객체를 UriComponentsBuilder로 구성
            URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("serviceKey", apiKey)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "AppTest")
                    .queryParam("_type", "json")
                    .queryParam("contentId", contentId)
                    .queryParam("contentTypeId", 12)
                    .queryParam("defaultYN", "Y")
                    .queryParam("firstImageYN", "Y")
                    .queryParam("areacodeYN", "Y")
                    .queryParam("catcodeYN", "Y")
                    .queryParam("addrinfoYN", "Y")
                    .queryParam("mapinfoYN", "Y")
                    .queryParam("overviewYN", "Y")
                    .queryParam("numOfRows", 1)
                    .queryParam("pageNo", 1)
                    .build()
                    .toUri();  // URI 객체로 변환

            Mono<String> response = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class);

            String jsonResponse = response.block();
            System.out.println("API Response: " + jsonResponse);

            // JSON 데이터 파싱하여 overview 가져오기
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
            JSONObject items = responseBody.getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");
            if (!itemArray.isEmpty()) {
                JSONObject item = itemArray.getJSONObject(0);
                String overview = item.getString("overview");

                // 3. `overview` 데이터 업데이트
                tourData.setIntroduction(overview);
                tourDataRepository.save(tourData);  // JPA를 통해 저장
            }
        }
        return tourDataRepository.findAll();
    }


    @Transactional
    public List<TourData> updateInfo() {
        String url = "https://apis.data.go.kr/B551011/KorService1/detailIntro1";
        // 1. 모든 TourData 엔티티 조회
        List<TourData> tourDataList = tourDataRepository.findAll();
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        // 2. 각 contentId로 API 호출 및 데이터 업데이트
//        for (int i = 0; i < 10; i++) {
        for (TourData tourData : tourDataList) {
//            TourData tourData = tourDataList.get(i);
            String contentId = tourData.getContentId();
            System.out.println(contentId);

            Mono<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("serviceKey", apiKey)  // Add your service key here
                            .queryParam("MobileOS", "ETC")
                            .queryParam("MobileApp", "AppTest")
                            .queryParam("_type", "json")
                            .queryParam("contentId", contentId)
                            .queryParam("contentTypeId", 12)
                            .queryParam("numOfRows", 10)  // Updated as per the provided link
                            .queryParam("pageNo", 1)      // Updated as per the provided link
                            .build())
                    .retrieve()
                    .bodyToMono(String.class);

            String jsonResponse = response.block();
            System.out.println("API Response: " + jsonResponse);
            // JSON 데이터 파싱하여 overview 가져오기
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
            JSONObject items = responseBody.getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");
            if (!itemArray.isEmpty()) {
                JSONObject item = itemArray.getJSONObject(0);
                String infocenter = item.getString("infocenter");
                String restdate = item.getString("restdate");
                String usetime = item.getString("usetime");
                String parking = item.getString("parking");
                boolean parkingAvailable = parking.equals("가능");
                String babycarriage = item.getString("chkbabycarriage");
                boolean babyCarriageAvailable = !babycarriage.equals("불가");
                String pet = item.getString("chkpet");
                boolean petAvailable = pet.isEmpty();
                String creditcard = item.getString("chkcreditcard");
                boolean creditCardAvailable = !creditcard.equals("없음");

                // 3. `introduction` 데이터 업데이트
                tourData.setInfoCenter(infocenter);
                tourData.setRestDate(restdate);
                tourDataRepository.save(tourData);  // JPA를 통해 저장
            }
        }
        return tourDataRepository.findAll();
    }

    public ApiResponse<List<TourDataDto>> getHomeTourData() {
        List<TourData> tourDataList = tourDataRepository.findRandomTourDatasWithImages();

        // AccommodationDTO 리스트에 데이터를 매핑
        List<TourDataDto> tourDataDtoList = tourDataList.stream().map(tourData -> {
            TourDataDto tourDataDto = new TourDataDto();
            tourDataDto.setContentId(tourData.getContentId());
            tourDataDto.setTitle(tourData.getTitle());
            tourDataDto.setAddress(tourData.getAddress());
            tourDataDto.setIntroduction(tourData.getIntroduction());
            tourDataDto.setRestDate(tourData.getRestDate());
            tourDataDto.setInfoCenter(tourData.getInfoCenter());

            // 이미지 리스트를 DTO에 추가
            List<String> imageUrls = tourData.getImages().stream()
                    .map(TourDataImage::getOriginimgurl)
                    .collect(Collectors.toList());
            tourDataDto.setOriginimgurl(imageUrls);

            return tourDataDto;
        }).collect(Collectors.toList());

        return ApiResponse.ok("관광지를 성공적으로 조회했습니다.", tourDataDtoList);
    }

    public TourDataDto getTourDataDetails(String contentsid) {
        // DB에서 TourData 조회
        Optional<TourData> tourDataOptional = tourDataRepository.findByContentId(contentsid);
        if (tourDataOptional.isPresent()) {
            TourData tourData = tourDataOptional.get();

            // TourDataBasicDto에 데이터 매핑
            TourDataDto tourDataDto = new TourDataDto();
            tourDataDto.setContentId(tourData.getContentId());
            tourDataDto.setTitle(tourData.getTitle());
            tourDataDto.setAddress(tourData.getAddress());
            tourDataDto.setIntroduction(tourData.getIntroduction());
            tourDataDto.setRestDate(tourData.getRestDate());
            tourDataDto.setInfoCenter(tourData.getInfoCenter());

            // 이미지 리스트를 DTO에 추가
            List<String> imageUrls = tourData.getImages().stream()
                    .map(image -> image.getOriginimgurl()) // 여기를 수정
                    .collect(Collectors.toList());
            tourDataDto.setOriginimgurl(imageUrls);

            return tourDataDto;
        } else {
            // 없을 경우 null 리턴 또는 예외 처리
            return null;
        }
    }

//    public List<TourDataBasicDto> getRandomTourDataDetailsWithImages() {
//        // DB에서 6개의 랜덤 이미지를 가진 TourData 조회
//        List<TourData> tourDatas = tourDataRepository.findRandomTourDatasWithImages();
//
//        // TourDataBasicDto 리스트에 데이터를 매핑
//        List<TourDataBasicDto> tourDataBasicDtoList = tourDatas.stream().map(tourData -> {
//            TourDataBasicDto tourDataBasicDto = new TourDataBasicDto();
//            tourDataBasicDto.setId(tourData.getId());
//            tourDataBasicDto.setTitle(tourData.getTitle());
//            tourDataBasicDto.setRoadaddress(tourData.getRoadAddress());
//            tourDataBasicDto.setIntroduction(tourData.getDescription());
//            tourDataBasicDto.setCheckintime(tourData.getRestDate());
//
//            // 이미지 리스트를 DTO에 추가
//            List<String> imageUrls = tourData.getImages().stream()
//                    .map(image -> image.getUrl()) // 람다 표현식으로 변경
//                    .collect(Collectors.toList());
//            tourDataBasicDto.setOriginimgurl(imageUrls);
//
//            return tourDataBasicDto;
//        }).collect(Collectors.toList());
//
//        return tourDataBasicDtoList;
//    }

    public void updateAllTourDataImageUrls() {
        // DB에서 모든 TourData 객체 가져오기
        List<TourData> tourDataList = tourDataRepository.findAll();

        // 각 id에 대해 originimgurls 업데이트
        for (TourData tourData : tourDataList) {
            updateTourDataImageUrls(tourData);  // TourData 객체 전달
        }
    }

    public void updateTourDataImageUrls(TourData tourData) {
        String tourDataId = tourData.getContentId();  // id 필드를 사용
        String url = "https://apis.data.go.kr/B551011/KorService1/detailImage1?serviceKey="+apiKey+"&MobileOS=ETC&MobileApp=AppTest&_type=json&contentId=" + tourDataId + "&imageYN=Y&subImageYN=Y&numOfRows=10&pageNo=1";

        WebClient webClient = WebClient.builder().build();

        try {
            System.out.println("Requesting URL: " + url);

            // API로부터 JSON 응답 가져오기
            String jsonResponse = webClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.out.println("Error Response: " + errorBody);
                                    return Mono.error(new RuntimeException("API 요청 실패"));
                                });
                    })
                    .bodyToMono(String.class)
                    .block();

            // JSON 응답 형식 확인
            if (!jsonResponse.startsWith("{") && !jsonResponse.startsWith("[")) {
                throw new RuntimeException("예상치 못한 응답 형식: " + jsonResponse);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode
                    .path("response")
                    .path("body")
                    .path("items")
                    .path("item");

            // 여러 개의 originimgurl을 가져옴
            List<String> originImgUrls = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                String originimgurl = itemNode.path("originimgurl").asText();
                if (!originimgurl.isEmpty()) {
                    originImgUrls.add(originimgurl);
                    System.out.println(originimgurl);

                    // TourDataImage 객체 생성 및 저장
                    TourDataImage tourDataImage = new TourDataImage();
                    tourDataImage.setOriginimgurl(originimgurl);
                    tourDataImage.setTourData(tourData);  // 이미 전달된 TourData 객체 사용
                    tourDataImageRepository.save(tourDataImage);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApiResponse<List<TourDataResponseDto>> getTourDataListByKeyword(String keyword) {
        List<TourData> tourDataList = tourDataRepository.findByTitleContaining(keyword);
        List<TourDataResponseDto> tourDataDtoList = new ArrayList<>();
        for(TourData tourData: tourDataList) {
            String[] arr = tourData.getAddress().substring(8).split(" ");
            TourDataResponseDto tourDataResponseDto = new TourDataResponseDto(
                    arr[0] + " " + arr[1],
                    tourData.getTitle(),
                    tourData.getContentId(),
                    tourData.getImages().isEmpty() ? null : tourData.getImages().get(0).getOriginimgurl(),
                    tourData.getXMap(),
                    tourData.getYMap(),
                    "tourData"
            );
            tourDataDtoList.add(tourDataResponseDto);
        }
        return ApiResponse.ok("관광지 정보를 성공적으로 검색했습니다.", tourDataDtoList);
    }

    public ApiResponse<List<TourDataResponseDto>> getTourDataListHome() {
        Random random= new Random();
        Set<Integer> randomNumbers = new HashSet<>();
        List<TourDataResponseDto> dtos= new ArrayList<>();
//        List<Restaurant> restaurants = restaurantRepository.findAll();
        Long totalCount = tourDataRepository.count();
        while (dtos.size() < 6) {
            int randomNumber = random.nextInt(totalCount.intValue()); // 저장된 restaurant 수로 할 것
            if (randomNumbers.contains(randomNumber)) {
                continue;
            }
            randomNumbers.add(randomNumber);
            Optional<TourData> optionalTourData = tourDataRepository.findById(Long.valueOf(randomNumber));
            if(optionalTourData.isEmpty()) {
                continue;
            }
            TourData tourData = optionalTourData.get();
            String[] arr = tourData.getAddress().substring(8).split(" ");
//                    restaurant.setAddr(arr[0] + " " + arr[1]);
            TourDataResponseDto tourDataResponseDto = new TourDataResponseDto(
                    arr[0] + " " + arr[1],
                    tourData.getTitle(),
                    tourData.getContentId(),
                    tourData.getImages().isEmpty() ? null : tourData.getImages().get(0).getOriginimgurl(),
                    tourData.getXMap(),
                    tourData.getYMap(),
                    "tourData"
            );
            dtos.add(tourDataResponseDto);
        }
        System.out.println(dtos.size());
        return ApiResponse.ok("관광지 정보를 성공적으로 조회했습니다.", dtos);
    }

    public ApiResponse<List<HomeResponseDto>> getHomeTourDataList() {
        long totalCount = tourDataRepository.count();
        Random random = new Random();
        List<HomeResponseDto> homeResponseDtoList = new ArrayList<>();

        // 3개의 이미지를 가진 레코드를 모을 때까지 반복
        while (homeResponseDtoList.size() < 3) {
            int randomOffset = random.nextInt((int) totalCount - 3); // 총 레코드 수에서 3개를 제외한 범위 내에서 랜덤 시작점 선택
            Pageable pageable = PageRequest.of(randomOffset, 3); // 한 번에 3개의 레코드 가져오기
            Page<TourData> tourDataPage = tourDataRepository.findAll(pageable); // Page 객체로 받음

            // 이미지를 가진 레코드만 필터링하여 DTO로 변환
            List<HomeResponseDto> filteredList = tourDataPage.getContent().stream()
                    .filter(tourData -> !tourData.getImages().isEmpty()) // 이미지를 가진 레코드만 필터링
                    .map(tourData -> {
                        String[] arr = tourData.getAddress().substring(8).split(" ");
                        return new HomeResponseDto(
                                tourData.getContentId(),
                                tourData.getTitle(),
                                arr[0] + " " + arr[1],
                                tourData.getImages().get(0).getOriginimgurl() // 첫 번째 이미지 가져오기
                        );
                    })
                    .toList();

            homeResponseDtoList.addAll(filteredList);
            homeResponseDtoList = homeResponseDtoList.stream().distinct().limit(3).collect(Collectors.toList());
        }
        return ApiResponse.ok("이미지를 포함한 관광지 정보를 성공적으로 조회했습니다.", homeResponseDtoList);
    }

    public ApiResponse<DetailResponseDto> getTourDataDetail(String id) {
        TourData tourData = findByContentId(id);
        WeatherResponseDto weather = weatherService.getWeatherByAddress(tourData.getAddress());

        DetailResponseDto detailResponseDto = new DetailResponseDto(
                tourData.getContentId(),
                tourData.getTitle(),
                tourData.getAddress(),
                tourData.getRestDate(),
                weather.weatherCondition(),
                weather.temperature(),
                tourData.getInfoCenter(),
                tourData.getIntroduction(),
                tourData.getImages().get(0).getOriginimgurl(),
                tourData.getXMap(),
                tourData.getYMap()
        );
        return ApiResponse.ok("해당 관광지의 상세 정보를 조회하였습니다", detailResponseDto);
    }

    public TourData findByContentId(String contentId) {
        Optional<TourData> tourDataOptional = tourDataRepository.findByContentId(contentId);
        if(tourDataOptional.isPresent())
            return tourDataOptional.get();
        else
            throw new RuntimeException("해당 ID의 관광지가 없습니다.");
    }

    public ApiResponse<List<String>> getHashtagsById(String id) {
        TourData tourData = findByContentId(id);
        List<String> hashtags = openAIService.getHashtags(tourData.getIntroduction());
        return ApiResponse.ok(hashtags);
    }


//    public ApiResponse<List> getTourDataByKeyword(String keyword) {
//        List<TourData> tourDataList = tourDataRepository.findByTitleContaining(keyword);
//        List<PartCafeResponseDto> partCafeResponseDtoList = new ArrayList<>();
//        for(TourData tourData: tourDataList) {
//            String[] arr = cafe.getAddr().substring(8).split(" ");
//            PartCafeResponseDto partCafeResponseDto = new PartCafeResponseDto(
//                    arr[0] + " " + arr[1],
//                    cafe.getTitle(),
//                    cafe.getContentId(),
//                    cafe.getCafeImages().isEmpty() ? null : cafe.getCafeImages().get(0).getOriginimgurl(),
//                    cafe.getXMap(),
//                    cafe.getYMap()
//            );
//            partCafeResponseDtoList.add(partCafeResponseDto);
//        }
//        return ApiResponse.ok("음식점 정보를 성공적으로 조회했습니다.", partCafeResponseDtoList);
//    }



    public ApiResponse<Page<SimpleInformation>> retrieveAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 1. Cafe 기준으로 페이징 처리된 데이터를 조회
        Page<SimpleInformation> cafePage = tourDataRepository.findAllSimple(pageable);

        // CustomPage 객체로 변환 (기존 페이지네이션 정보와 category를 함께 담음)
        CustomPage<SimpleInformation> customPage = new CustomPage<>(
                cafePage.getContent(), pageable, cafePage.getTotalElements(), Category.TOURDATA.getValue());

        // ApiResponse로 반환
        return ApiResponse.ok("관광지 목록을 성공적으로 조회했습니다.", customPage);
    }
}

