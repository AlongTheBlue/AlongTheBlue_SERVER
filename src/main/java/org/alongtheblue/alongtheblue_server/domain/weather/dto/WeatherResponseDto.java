package org.alongtheblue.alongtheblue_server.domain.weather.dto;

public record WeatherResponseDto(
        String weatherCondition,
        String temperature
) {

}
