package org.alongtheblue.alongtheblue_server.global.data.tourData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourDataService {
    @Autowired
    private TourDataRepository tourDataRepository;
    private static final String SERVICE_KEY = "GY8BQwWZJD6QX3tfaQTpfYMRjcRnaHoPAxn/7u6ZffwScPHeO3TYZgA0zMPfnO/iSc/PunU/5rZYIa5jj98sUw==";

    public ArrayList<TourData> getTourData() {
        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
        ArrayList<TourData> tourDataList = new ArrayList<>();
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        // 먼저 totalCount 값을 가져옴
        int totalCount = getTotalCount(webClient);
        int numOfRows = 10;  // 한 페이지에 가져올 데이터 수
        int totalPages = (int) Math.ceil((double) totalCount / numOfRows);  // 총 페이지 수 계산

        // 페이지별로 데이터를 가져오는 반복문
        for (int pageNo = 1; pageNo <= totalPages; pageNo++) {
            int finalPageNo = pageNo;
            Mono<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("serviceKey", SERVICE_KEY)
                            .queryParam("numOfRows", numOfRows)
                            .queryParam("pageNo", finalPageNo)  // 페이지 번호 변경
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

                TourData tourData = TourData.builder()
                        .id(id)
                        .title(title)
                        .roadAddress(address)
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
                        .queryParam("serviceKey", SERVICE_KEY)
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
    public List<TourData> updateOverviews() {
        String url = "https://apis.data.go.kr/B551011/KorService1/detailCommon1";
        // 1. 모든 TourData 엔티티 조회
        List<TourData> tourDataList = tourDataRepository.findAll();
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        // 2. 각 contentId로 API 호출 및 데이터 업데이트
        for(int i=500;i<tourDataList.size();i++){
//        for (TourData tourData : tourDataList) {
            TourData tourData = tourDataList.get(i);
            String contentId = tourData.getId();
            System.out.println(contentId);
            Mono<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("serviceKey", SERVICE_KEY)
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
                String overview = item.getString("overview");

                // 3. `overview` 데이터 업데이트
                tourData.setDescription(overview);
                tourDataRepository.save(tourData);  // JPA를 통해 저장
            }
        }
        return tourDataRepository.findAll();
    }

    @Transactional
    public List<TourData> updateIntroduction() {
        String url = "https://apis.data.go.kr/B551011/KorService1/detailIntro1";
        // 1. 모든 TourData 엔티티 조회
        List<TourData> tourDataList = tourDataRepository.findAll();
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        // 2. 각 contentId로 API 호출 및 데이터 업데이트
        for(int i=500;i<tourDataList.size();i++){
//        for (TourData tourData : tourDataList) {
            TourData tourData = tourDataList.get(i);
            String contentId = tourData.getId();
            System.out.println(contentId);

            Mono<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("serviceKey", SERVICE_KEY)  // Add your service key here
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
                tourData.setUseTime(usetime);
                tourData.setParking(parking);
                tourData.setBabyCarriage(babycarriage);
                tourData.setPet(pet);
                tourData.setCreditCard(creditcard);

                tourDataRepository.save(tourData);  // JPA를 통해 저장
            }
        }
        return tourDataRepository.findAll();
    }

    public ArrayList<TourData> getHomeTourData() {
        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        int total = getTotalCount(webClient);
        ArrayList<TourData> tourDataList = new ArrayList<>();


        return tourDataList;
    }
}

