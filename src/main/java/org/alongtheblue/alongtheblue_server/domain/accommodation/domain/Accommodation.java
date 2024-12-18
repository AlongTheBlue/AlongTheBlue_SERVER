package org.alongtheblue.alongtheblue_server.domain.accommodation.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentId;
    private String title;
    private String address;
    @Column(columnDefinition = "TEXT")
    private String introduction;
    private String infoCenter;
    private String checkintime;
    private String xMap;
    private String yMap;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AccommodationImage> images;

    public Accommodation(String contentId, String title, String address, String xMap, String yMap) {
        this.contentId = contentId;
        this.title = title;
        this.address = address;
        this.xMap = xMap;
        this.yMap = yMap;
    }
}
