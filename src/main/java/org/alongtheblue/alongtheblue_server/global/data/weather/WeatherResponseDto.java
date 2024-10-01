package org.alongtheblue.alongtheblue_server.global.data.weather;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public record WeatherResponseDto(
        String weatherCondition,
        String temperature
) {

}
