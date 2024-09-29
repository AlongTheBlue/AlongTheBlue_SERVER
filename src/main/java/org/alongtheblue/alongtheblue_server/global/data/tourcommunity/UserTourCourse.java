package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class UserTourCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdate;
    private String title;
    private String writing;

    @OneToMany(mappedBy = "userTourCourse", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TourPostItem> tourPostItems;

    @OneToMany(mappedBy = "tourCourseForHashTag", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TourPostHashTag> tourPostHashTags;
}
