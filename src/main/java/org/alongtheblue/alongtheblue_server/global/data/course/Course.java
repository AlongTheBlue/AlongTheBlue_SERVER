package org.alongtheblue.alongtheblue_server.global.data.course;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.alongtheblue.alongtheblue_server.global.data.courseHashTag.CourseHashTag;
import org.alongtheblue.alongtheblue_server.global.data.courseItem.CourseItem;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subtitle;
    private String content;
    private Date createdate;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CourseItem> courseItemList;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CourseHashTag> courseHashTags;

}
