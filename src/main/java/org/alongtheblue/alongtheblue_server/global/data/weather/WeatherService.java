package org.alongtheblue.alongtheblue_server.global.data.weather;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final AddressSearchService addressSearchService;
    private final JejuDivisonRepository jejuDivisonRepository;

    @Value("${api.key}")
    private String apiKey;

    public WeatherResponseDto getWeatherByAddress(String address) {
        String regionCode = addressSearchService.searchRegionCodeByAddress(address);
        System.out.println("regionCode: "+regionCode);
        if(regionCode.isEmpty()) {
            String[] addressArr = address.split(" ");
            String city = addressArr[1];
            regionCode = jejuDivisonRepository.findByCityAndDistrict(city,"").getDivisionId();
        }
        JejuDivision jejuDivision = jejuDivisonRepository.findByDivisionId(regionCode);
        System.out.println("jejuDivision: "+jejuDivision.getDivisionId());
        Optional<Weather> weather = weatherRepository.findByJejuDivision(jejuDivision);
        return weather.map(value -> new WeatherResponseDto(
                value.getWeatherCondition(),
                value.getTemperature()
        )).orElse(null); // 오류 처리 해야함
    }

    public List<Weather> saveRecentWeather(){
        List<JejuDivision> jejuDivisionList = jejuDivisonRepository.findAll();
        List<Weather> weatherList = new ArrayList<>();
        for (JejuDivision jejuDivision : jejuDivisionList) {
            Date date = new Date();
            Weather weather = saveWeatherByDivision(jejuDivision, date);
            weatherRepository.save(weather);
            weatherList.add(weather);
        }
        return weatherList;
    }

    public Weather saveWeatherByDivision(JejuDivision jejuDivision, Date date){
        String baseUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        WebClient webClient = WebClient.builder().build();

        // 1. 현재 날짜와 시간을 기반으로 baseDate와 baseTime 계산
        String baseDate = getBaseDate(); // 날짜를 하루 전날로 계산할 수 있도록 변경된 부분
        String baseTime = getBaseTime(); // 3시간 간격으로 계산된 baseTime을 가져옴
        String nx = jejuDivision.getX();
        String ny = jejuDivision.getY();

        // URI 객체를 UriComponentsBuilder로 구성
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "12")
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate) // 계산된 baseDate 사용
                .queryParam("base_time", baseTime)
                .queryParam("nx", nx)
                .queryParam("ny", ny)
                .build()
                .toUri();  // URI 객체로 변환

        // WebClient로 API 호출
        Mono<String> response = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);

        // JSON 응답 데이터 파싱
        String jsonResponse = response.block();
        System.out.println(jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
        JSONArray itemArray = responseBody.getJSONObject("items").getJSONArray("item");

        // 날씨 상태 및 기온 추출
        String sky = extractCategoryData(itemArray, "SKY"); // 하늘 상태
        String pty = extractCategoryData(itemArray, "PTY"); // 강수 형태
        String temperature = extractCategoryData(itemArray, "TMP"); // 기온

        // 날씨 상태 판단 (흐림, 눈, 비, 맑음 등)
        String weatherCondition = determineWeatherCondition(sky, pty);

        // 이미 존재하는 날씨 데이터 조회
        Optional<Weather> existingWeather = weatherRepository.findByJejuDivision(jejuDivision);

        Weather weather;
        if (existingWeather.isPresent())
            weather = existingWeather.get();
        else {
            weather = new Weather();
            weather.setJejuDivision(jejuDivision);
        }
        weather.setWeatherCondition(weatherCondition);
        weather.setTemperature(temperature);
        return weatherRepository.save(weather);
    }


    // 특정 카테고리 데이터를 추출하는 헬퍼 메서드
    private String extractCategoryData(JSONArray itemArray, String category) {
        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            if (item.getString("category").equals(category))
                return item.getString("fcstValue");
        }
        return null; // 해당 카테고리 데이터가 없는 경우
    }

    // 하늘 상태(SKY)와 강수 형태(PTY)를 기반으로 날씨 상태를 결정하는 메서드
    private String determineWeatherCondition(String sky, String pty) {
        // 강수 형태(PTY) 우선적으로 판단
        if (!"0".equals(pty)) {
            switch (pty) {
                case "1", "4" -> { return "비"; }     // 비 또는 소나기
                case "2" -> { return "비"; }          // 비/눈도 비로 처리
                case "3" -> { return "눈"; }          // 눈
            }
        }

        // 강수 형태가 없을 때 하늘 상태(SKY) 판단
        switch (sky) {
            case "1" -> { return "맑음"; }        // 맑음
            case "3" -> { return "구름많음"; }   // 구름많음
            case "4" -> { return "흐림"; }       // 흐림
            default -> { return "알 수 없음"; }  // 알 수 없는 날씨 상태
        }
    }

    private String getBaseTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -2); // 현재 시간에서 2시간을 뺌

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String baseTime;

        // 가장 가까운 3시간 단위의 시간을 구함 (02, 05, 08, 11, 14, 17, 20, 23)
        if (hour >= 23) {
            baseTime = "2300";
        } else if (hour >= 20) {
            baseTime = "2000";
        } else if (hour >= 17) {
            baseTime = "1700";
        } else if (hour >= 14) {
            baseTime = "1400";
        } else if (hour >= 11) {
            baseTime = "1100";
        } else if (hour >= 8) {
            baseTime = "0800";
        } else if (hour >= 5) {
            baseTime = "0500";
        } else {
            baseTime = "0200";
        }

        return baseTime;
    }

    private String getBaseDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -2); // 현재 시간에서 2시간을 뺌

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        // 만약 시간대가 02시 이전이라면 하루 전날을 기준으로 함
        if (hour < 2) {
            cal.add(Calendar.DATE, -1);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(cal.getTime());
    }

}
