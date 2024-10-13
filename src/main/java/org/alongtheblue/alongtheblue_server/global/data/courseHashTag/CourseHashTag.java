package org.alongtheblue.alongtheblue_server.global.data.courseHashTag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.alongtheblue.alongtheblue_server.global.data.course.Course;

@Entity
@Data
public class CourseHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "course")
    @JsonBackReference
    private Course course;
}
