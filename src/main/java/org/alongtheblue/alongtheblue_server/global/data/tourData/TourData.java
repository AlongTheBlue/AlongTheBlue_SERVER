package org.alongtheblue.alongtheblue_server.global.data.tourData;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class TourData {
    @Id
    private String id;
    private String title;
    private String roadAddress;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String infoCenter;
    @Column(columnDefinition = "TEXT")
    private String useTime;
    private String restDate;
    private String parking;
    private String creditCard;
    private String babyCarriage;
    private String pet;
    @Builder
    public TourData(String id, String title, String roadAddress, String description, String infoCenter, String useTime, String restDate, String parking, String creditCard, String babyCarriage, String pet) {
        this.id = id;
        this.title = title;
        this.roadAddress = roadAddress;
        this.infoCenter = infoCenter;
        this.description = description;
        this.useTime = useTime;
        this.restDate = restDate;
        this.parking = parking;
        this.creditCard = creditCard;
        this.babyCarriage = babyCarriage;
        this.pet = pet;
    }

    @OneToMany(mappedBy = "tourData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourDataImage> images; // 추가된 부분


    public TourData() {

    }
}
