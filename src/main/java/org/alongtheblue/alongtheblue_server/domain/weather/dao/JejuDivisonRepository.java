package org.alongtheblue.alongtheblue_server.domain.weather.dao;

import org.alongtheblue.alongtheblue_server.domain.weather.domain.JejuDivision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JejuDivisonRepository extends JpaRepository<JejuDivision, Long> {

    JejuDivision findByDivisionId(String regionCode);

    JejuDivision findByCityAndDistrict(String city, String district);
}
