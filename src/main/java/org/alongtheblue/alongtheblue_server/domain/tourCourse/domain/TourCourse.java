package org.alongtheblue.alongtheblue_server.domain.tourCourse.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.courseLike.domain.CourseLike;
import org.alongtheblue.alongtheblue_server.domain.hashTag_course.domain.HashTag_Course;
import org.alongtheblue.alongtheblue_server.domain.tourCourse_item.domain.TourCourse_Item;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TourCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String courseName;

    @NotNull
    private Date date;

    @NotNull
    private Long likeCount;

    @NotNull
    private Boolean isPosted;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private UserInfo userInfo;

    @OneToMany(mappedBy = "tourCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CourseLike> courseLikes = new ArrayList<>();

    @OneToMany(mappedBy = "tourCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TourCourse_Item> tourCourseItems = new ArrayList<>();

    @OneToMany(mappedBy = "tourCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HashTag_Course> hashTagCourses = new ArrayList<>();

    @Builder
    public TourCourse(String courseName, Date date, Long likeCount, Boolean isPosted) {
        this.courseName = courseName;
        this.date = date;
        this.likeCount = likeCount;
        this.isPosted = isPosted;
    }
}
