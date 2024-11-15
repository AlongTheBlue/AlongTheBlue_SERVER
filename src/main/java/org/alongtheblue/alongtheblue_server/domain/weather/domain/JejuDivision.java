package org.alongtheblue.alongtheblue_server.domain.weather.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JejuDivision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String divisionId;
    private String province;
    private String city;
    private String district;
    private String x;
    private String y;

    @OneToOne(mappedBy = "jejuDivision", cascade = CascadeType.ALL)
    private Weather weather;

    public JejuDivision(String divisionId, String province, String city, String district, String x, String y) {
        this.divisionId = divisionId;
        this.province = province;
        this.city = city;
        this.district = district;
        this.x = x;
        this.y = y;
    }

    // Default constructor required by JPA
    public JejuDivision() {}
}
