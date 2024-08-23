package org.alongtheblue.alongtheblue_server.domain.tourCourse_item.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.tourCourse.domain.TourCourse;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TourCourse_Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date date;

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    @JsonBackReference
    private TourCourse tourCourse;

    @Builder
    public TourCourse_Item(Date date) {
        this.date = date;
    }
}
