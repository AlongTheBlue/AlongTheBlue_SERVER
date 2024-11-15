package org.alongtheblue.alongtheblue_server.domain.weather.api;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.weather.application.WeatherService;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.domain.weather.dto.WeatherResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping()
    public WeatherResponseDto getWeather(@RequestParam String address) {
        return weatherService.getWeatherByAddress(address);
    }

    @GetMapping("/save")
    public ApiResponse<String> saveWeather() {
        weatherService.saveRecentWeather();
        return ApiResponse.ok("날씨 업데이트");
    }
}
