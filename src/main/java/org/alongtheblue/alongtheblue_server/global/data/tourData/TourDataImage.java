package org.alongtheblue.alongtheblue_server.global.data.tourData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TourDataImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;

    // TourImageEntity에 맞추어 변경
    @ManyToOne
    @JoinColumn(name = "tour_data") // TourImageEntity의 ID에 해당하는 외래 키
    @JsonBackReference
    private TourData tourData; // Accommodation 대신 TourImage와 관계 설정
}
