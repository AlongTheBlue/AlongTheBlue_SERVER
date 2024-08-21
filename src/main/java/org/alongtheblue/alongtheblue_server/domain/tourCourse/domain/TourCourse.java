package org.alongtheblue.alongtheblue_server.domain.tourCourse.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;

import java.util.Date;

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

    @Builder
    public TourCourse(String courseName, Date date, Long likeCount, Boolean isPosted) {
        this.courseName = courseName;
        this.date = date;
        this.likeCount = likeCount;
        this.isPosted = isPosted;
    }
}
