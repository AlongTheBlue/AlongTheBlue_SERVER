package org.alongtheblue.alongtheblue_server.global.data.weather;

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
}
