package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;



@Service
public class AccommodationService {
    private final WebClient webClient;

    @Autowired
    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationService(WebClient.Builder webClientBuilder, AccommodationRepository accommodationRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.visitjeju.net").build();
        this.accommodationRepository = accommodationRepository;
    }

    public void fetchHotelData() {
        Mono<String> response = webClient.get()
                .uri("/vsjApi/contents/searchList?apiKey=ca0cc07e23f9458d8434fd02261340ad&locale=kr&category=c3")
                .retrieve()
                .bodyToMono(String.class);

        response.subscribe(this::parseAndSaveHotelData);
    }

    private void parseAndSaveHotelData(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode.path("items");

            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    String contentsid = itemNode.path("contentsid").asText();
                    String title = itemNode.path("title").asText();
                    String roadaddress = itemNode.path("roadaddress").asText();
                    String introduction = itemNode.path("introduction").asText();

                    saveHotelData(contentsid, title, roadaddress, introduction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveHotelData(String contentsid, String title, String roadaddress, String introduction) {
        Accommodation accommodation = new Accommodation();
        accommodation.setContentsid(contentsid);
        accommodation.setTitle(title);
        accommodation.setRoadaddress(roadaddress);
        accommodation.setIntroduction(introduction);

        // 데이터를 데이터베이스에 저장
        accommodationRepository.save(accommodation);
    }

    public void saveAccommodations() {
        String url = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=ca0cc07e23f9458d8434fd02261340ad&locale=kr&category=c3";
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(url, String.class);

        try {
            // JSON 데이터를 파싱하여 필요한 필드 추출
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode.path("items");

            List<Accommodation> accommodations = new ArrayList<>();

            for (JsonNode itemNode : itemsNode) {
                String contentsid = itemNode.path("contentsid").asText();
                String title = itemNode.path("title").asText();
                String roadaddress = itemNode.path("roadaddress").asText();
                String introduction = itemNode.path("introduction").asText();

                Accommodation accommodation = new Accommodation();
                accommodation.setContentsid(contentsid);
                accommodation.setTitle(title);
                accommodation.setRoadaddress(roadaddress);
                accommodation.setIntroduction(introduction);

                accommodations.add(accommodation);

                // 100개의 데이터만 저장
                if (accommodations.size() >= 100) {
                    break;
                }
            }

            // 데이터베이스에 저장
            accommodationRepository.saveAll(accommodations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOverview() {
        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=vylaYmHlZKml%2B3CSbr0P4dfuH7mKcn3e%2FOC5oro4WRwqf4gRk7yclBEl9H86qJSzgsLCSaqUR8ZgkjlxxfMuPA%3D%3D&numOfRows=255&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&contentTypeId=32&areaCode=39";
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(url, String.class);

        try {
            // JSON 데이터를 파싱하여 필요한 필드 추출
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            List<Accommodation> accommodations = new ArrayList<>();

            for (JsonNode itemNode : itemsNode) {
                String contentsid = itemNode.path("contentid").asText();
                String addr1 = itemNode.path("addr1").asText();
                String title = itemNode.path("title").asText();

                Accommodation accommodation = new Accommodation();
                accommodation.setContentsid(contentsid);
                accommodation.setRoadaddress(addr1);
                accommodation.setTitle(title);

                accommodations.add(accommodation);

                // 100개의 데이터만 저장 (필요에 따라 조정 가능)
                if (accommodations.size() >= 100) {
                    break;
                }
            }

            // 데이터베이스에 저장
            accommodationRepository.saveAll(accommodations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void updateAccommodationInfo() {
//        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=vylaYmHlZKml%2B3CSbr0P4dfuH7mKcn3e%2FOC5oro4WRwqf4gRk7yclBEl9H86qJSzgsLCSaqUR8ZgkjlxxfMuPA%3D%3D&numOfRows=255&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&contentTypeId=32&areaCode=39";
//        RestTemplate restTemplate = new RestTemplate();
//        String jsonResponse = restTemplate.getForObject(url, String.class);
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(jsonResponse);
//            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
//
//            List<Accommodation> accommodations = new ArrayList<>();
//
//            for (JsonNode itemNode : itemsNode) {
//                String contentsid = itemNode.path("contentid").asText();
//                String addr1 = itemNode.path("addr1").asText();
//                String title = itemNode.path("title").asText();
//
//                Accommodation accommodation = new Accommodation();
//                accommodation.setContentsid(contentsid);
//                accommodation.setRoadaddress(addr1);
//                accommodation.setTitle(title);
//
//                // 새로 추가한 필드
//                accommodation.setAddr1(addr1);
//                accommodation.setContentid(contentsid);
//
//                accommodations.add(accommodation);
//
//                if (accommodations.size() >= 100) {
//                    break;
//                }
//            }
//
//            accommodationRepository.saveAll(accommodations);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void updateAccommodationInfo() {
        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=GY8BQwWZJD6QX3tfaQTpfYMRjcRnaHoPAxn/7u6ZffwScPHeO3TYZgA0zMPfnO/iSc/PunU/5rZYIa5jj98sUw==&numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&contentTypeId=32&areaCode=39";

        webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(this::processAccommodationData); // 데이터 처리 메서드 호출

    }

    // JSON 데이터 처리 로직
    private void processAccommodationData(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            List<Accommodation> accommodations = new ArrayList<>();

            for (JsonNode itemNode : itemsNode) {
                String contentsid = itemNode.path("contentid").asText();
                String addr1 = itemNode.path("addr1").asText();
                String title = itemNode.path("title").asText();

                Accommodation accommodation = new Accommodation();
                accommodation.setContentsid(contentsid);
                accommodation.setRoadaddress(addr1);
                accommodation.setTitle(title);

                // 추가된 필드 설정


                accommodations.add(accommodation);

                if (accommodations.size() >= 100) {
                    break;
                }
            }

            // 데이터베이스에 저장
            accommodationRepository.saveAll(accommodations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public List<Accommodation> getAccommodationsWithNullIntroduction() {
//        List<Accommodation> accommodationList = accommodationRepository.findByIntroductionIsNull();
//        for (int i = 0; i < accommodationList.size(); i++) {
//            System.out.println(accommodationList.get(i).getContentsid());
//        }
//
//
//    }

//    public void updateAccommodationsWithNullIntroduction() {
//        List<Accommodation> accommodationList = accommodationRepository.findByIntroductionIsNull();
//        WebClient webClient = WebClient.create("https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=vylaYmHlZKml%2B3CSbr0P4dfuH7mKcn3e%2FOC5oro4WRwqf4gRk7yclBEl9H86qJSzgsLCSaqUR8ZgkjlxxfMuPA%3D%3D&numOfRows=255&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&contentTypeId=32&areaCode=39");
//
//        for (Accommodation accommodation : accommodationList) {
//            String contentsid = accommodation.getContentsid();
//            String apiUrl = "/info?contentsid=" + contentsid;
//
//            try {
//                ApiResponse response = webClient.get()
//                        .uri(apiUrl)
//                        .retrieve()
//                        .bodyToMono(ApiResponse.class)
//                        .block();  // 비동기 처리 대신 동기적으로 결과를 받음
//
//                if (response != null && response.getIntroduction() != null) {
//                    accommodation.setIntroduction(response.getIntroduction());
//                    accommodationRepository.save(accommodation);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();  // 에러 발생 시 출력
//            }
//        }
//    }

    // API 응답을 매핑하기 위한 클래스
    public static class ApiResponse {
        private String introduction;

        // Getters and setters
        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }

    public void updateAccommodationsWithNullIntroduction() {
        // contentsid 값들만 가져옴
        List<String> contentsIds = accommodationRepository.findAllContentsIds();
        WebClient webClient = WebClient.create();

        for (String contentsid : contentsIds) {
            try {
                ApiResponse response = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .scheme("https")
                                .host("apis.data.go.kr")
                                .path("/B551011/KorService1/areaBasedList1")
                                .queryParam("serviceKey", "GY8BQwWZJD6QX3tfaQTpfYMRjcRnaHoPAxn/7u6ZffwScPHeO3TYZgA0zMPfnO/iSc/PunU/5rZYIa5jj98sUw==")
                                .queryParam("numOfRows", 255)
                                .queryParam("pageNo", 1)
                                .queryParam("MobileOS", "ETC")
                                .queryParam("MobileApp", "AppTest")
                                .queryParam("_type", "json")
                                .queryParam("listYN", "Y")
                                .queryParam("arrange", "A")
                                .queryParam("contentTypeId", 32)
                                .queryParam("areaCode", 39)
                                .queryParam("contentsid", contentsid)  // contentsid 추가
                                .build())
                        .retrieve()
                        .bodyToMono(ApiResponse.class)
                        .block();  // 동기 호출

                // API 응답으로 introduction 필드가 있는 경우 업데이트
                if (response != null && response.getIntroduction() != null) {
                    // 데이터베이스에서 해당 contentsid를 가진 Accommodation 객체를 찾아 업데이트
                    Accommodation accommodation = accommodationRepository.findById(contentsid).orElseThrow(() ->
                            new IllegalArgumentException("Accommodation not found for contentsid: " + contentsid));
                    accommodation.setIntroduction(response.getIntroduction());
                    accommodationRepository.save(accommodation);  // 데이터베이스에 저장
                }
            } catch (Exception e) {
                e.printStackTrace();  // 예외 발생 시 출력
            }
        }
    }
}
