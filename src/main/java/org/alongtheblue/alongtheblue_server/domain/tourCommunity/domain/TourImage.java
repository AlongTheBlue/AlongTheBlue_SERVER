package org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
public class TourImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String url;

    @ManyToOne
    @JoinColumn(name = "tourPostItem")
    @JsonBackReference
    private TourPostItem tourPostItem;

    @Builder
    public TourImage(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    public TourImage() {

    }
}
