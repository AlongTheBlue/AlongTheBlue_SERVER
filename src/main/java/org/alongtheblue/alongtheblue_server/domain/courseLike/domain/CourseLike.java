package org.alongtheblue.alongtheblue_server.domain.courseLike.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.alongtheblue.alongtheblue_server.domain.item.domain.Item;
import org.alongtheblue.alongtheblue_server.domain.tourCourse.domain.TourCourse;
import org.alongtheblue.alongtheblue_server.domain.tourCourse_item.domain.TourCourse_Item;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseLike {
    @EmbeddedId
    private CourseLike.CourseLikeId id;

    private Date date;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private UserInfo userInfo;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "courseId", nullable = false)
    @JsonBackReference
    private TourCourse tourCourse;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CourseLikeId implements Serializable {
        private Long userId;
        private Long courseId;
    }
}
