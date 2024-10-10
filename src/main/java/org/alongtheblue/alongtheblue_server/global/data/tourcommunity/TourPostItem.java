package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

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
    private String x;
    private String y;

    @ManyToOne
    @JoinColumn(name = "userTourCourse")
    @JsonBackReference
    private UserTourCourse userTourCourse;

    @OneToMany(mappedBy = "tourPostItem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TourImage> tourImage;

    // String contentId;
}
