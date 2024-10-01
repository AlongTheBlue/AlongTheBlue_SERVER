package org.alongtheblue.alongtheblue_server.global.data.weather;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findByJejuDivision(JejuDivision jejuDivision);
}
