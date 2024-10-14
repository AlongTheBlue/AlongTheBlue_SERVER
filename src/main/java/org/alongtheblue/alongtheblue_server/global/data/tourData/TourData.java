package org.alongtheblue.alongtheblue_server.global.data.tourData;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class TourData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contentId;
    private String title;
    private String address;
    @Column(columnDefinition = "TEXT")
    private String introduction;
    @Column(columnDefinition = "TEXT")
    private String infoCenter;
    private String restDate;
    private String xMap;
    private String yMap;

    @Builder
    public TourData(String contentId, String title, String address, String infoCenter, String restDate, String xMap, String yMap) {
        this.contentId = contentId;
        this.title = title;
        this.infoCenter = infoCenter;
        this.restDate = restDate;
        this.address = address;
        this.xMap = xMap;
        this.yMap = yMap;
    }

    @OneToMany(mappedBy = "tourData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourDataImage> images; // 추가된 부분

    public TourData() {

    }
}
