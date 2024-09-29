package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.alongtheblue.alongtheblue_server.global.data.tourData.TourData;

@Entity
@Data
public class TourImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "tourPostItem")
    @JsonBackReference
    private TourPostItem tourPostItem;

    @ManyToOne
    @JoinColumn(name = "tour_data_id") // 외래 키 컬럼 이름
    private TourData tourData; // TourData와의 관계 설정
}
