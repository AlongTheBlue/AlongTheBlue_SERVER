package org.alongtheblue.alongtheblue_server.domain.weather.dao;

import org.alongtheblue.alongtheblue_server.domain.weather.domain.JejuDivision;
import org.alongtheblue.alongtheblue_server.domain.weather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findByJejuDivision(JejuDivision jejuDivision);
}
