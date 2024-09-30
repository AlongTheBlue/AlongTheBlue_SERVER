package org.alongtheblue.alongtheblue_server.global.data.tourData;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.TourImage;

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
    private String infoCenter;
    private String restDate;
    @Builder
    public TourData(String contentId, String title, String address, String infoCenter, String restDate) {
        this.contentId = contentId;
        this.title = title;
        this.infoCenter = infoCenter;
        this.restDate = restDate;
        this.address = address;
    }

    @OneToMany(mappedBy = "tourData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourDataImage> images; // 추가된 부분

    public TourData() {

    }
}
