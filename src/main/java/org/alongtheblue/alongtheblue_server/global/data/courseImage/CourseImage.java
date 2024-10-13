package org.alongtheblue.alongtheblue_server.global.data.courseImage;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.alongtheblue.alongtheblue_server.global.data.courseItem.CourseItem;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.TourPostItem;

@Entity
@Data
public class CourseImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "courseItem")
    @JsonBackReference
    private CourseItem courseItem;
}
