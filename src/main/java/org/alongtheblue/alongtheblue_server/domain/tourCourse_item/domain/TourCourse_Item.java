package org.alongtheblue.alongtheblue_server.domain.tourCourse_item.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.alongtheblue.alongtheblue_server.domain.hashTag.domain.HashTag;
import org.alongtheblue.alongtheblue_server.domain.hashTag_item.domain.HashTag_item;
import org.alongtheblue.alongtheblue_server.domain.item.domain.Item;
import org.alongtheblue.alongtheblue_server.domain.tourCourse.domain.TourCourse;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TourCourse_Item {
    @EmbeddedId
    private TourCourse_Item.TourCourseItemId id;

    private Date date;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "itemId", nullable = false)
    @JsonBackReference
    private Item item;

    @ManyToOne
    @MapsId("tid")
    @JoinColumn(name = "tid", nullable = false)
    @JsonBackReference
    private TourCourse tourCourse;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class TourCourseItemId implements Serializable {
        private Long itemId;
        private Long tid;
    }
}
