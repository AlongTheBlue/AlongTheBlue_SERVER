package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

// AlongCourseItem
@Entity
@Data
public class TourPostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;    // title로 변경
    private String category;
    private String address;
    private String comment; // content로 변경
    private String xMap;
    private String yMap;
    private String contentId;

    @ManyToOne
    @JoinColumn(name = "userTourCourse")
    @JsonBackReference
    private UserTourCourse userTourCourse;

    @OneToMany(mappedBy = "tourPostItem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TourImage> tourImage;

    @Builder
    public TourPostItem(String name, String category, String address, String comment, String xMap, String yMap,
                        String contentId, UserTourCourse userTourCourse, List<TourImage> tourImage) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.comment = comment;
        this.xMap = xMap;
        this.yMap = yMap;
        this.contentId = contentId;
        this.userTourCourse = userTourCourse;
        this.tourImage = tourImage;
    }

    public TourPostItem() {

    }
}
