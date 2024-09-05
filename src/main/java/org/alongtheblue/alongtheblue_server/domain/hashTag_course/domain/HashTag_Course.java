package org.alongtheblue.alongtheblue_server.domain.hashTag_course.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.alongtheblue.alongtheblue_server.domain.hashTag.domain.HashTag;
import org.alongtheblue.alongtheblue_server.domain.hashTag_item.domain.HashTag_item;
import org.alongtheblue.alongtheblue_server.domain.item.domain.Item;
import org.alongtheblue.alongtheblue_server.domain.tourCourse.domain.TourCourse;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag_Course {

    @EmbeddedId
    private HashTag_Course.HashTagCourseId id;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "courseId", nullable = false)
    @JsonBackReference
    private TourCourse tourCourse;

    @ManyToOne
    @MapsId("hid")
    @JoinColumn(name = "hid", nullable = false)
    @JsonBackReference
    private HashTag hashTag;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class HashTagCourseId implements Serializable {
        private Long courseId;
        private Long hid;
    }
}
