package org.alongtheblue.alongtheblue_server.domain.weather.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String weatherCondition;
    private String temperature;

    @OneToOne
    @JoinColumn(name = "jeju_division_id")
    private JejuDivision jejuDivision;
}