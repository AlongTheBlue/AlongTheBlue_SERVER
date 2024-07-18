package org.alongtheblue.alongtheblue_server.domain.tourCourse.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public TourCourse(String courseName, Date date, Long likeCount, Boolean isPosted) {
        this.courseName = courseName;
        this.date = date;
        this.likeCount = likeCount;
        this.isPosted = isPosted;
    }
}
