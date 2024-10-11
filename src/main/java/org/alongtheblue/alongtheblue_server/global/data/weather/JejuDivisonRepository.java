package org.alongtheblue.alongtheblue_server.global.data.weather;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JejuDivisonRepository extends JpaRepository<JejuDivision, Long> {

    JejuDivision findByDivisionId(String regionCode);

    JejuDivision findByCityAndDistrict(String city, String district);
}
