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
import org.alongtheblue.alongtheblue_server.domain.search.domain.Search;
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

    @Builder
    public TourCourse(String courseName, Date date, Long likeCount, Boolean isPosted) {
        this.courseName = courseName;
        this.date = date;
        this.likeCount = likeCount;
        this.isPosted = isPosted;
    }
}
