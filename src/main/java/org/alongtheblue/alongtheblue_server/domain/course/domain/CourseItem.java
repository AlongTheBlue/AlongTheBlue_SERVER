package org.alongtheblue.alongtheblue_server.domain.course.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CourseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String address;
    private String content;
    private String xMap;
    private String yMap;
    private String contentId;

    @ManyToOne
    @JoinColumn(name = "course")
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "courseItem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CourseImage> tourImage;

}