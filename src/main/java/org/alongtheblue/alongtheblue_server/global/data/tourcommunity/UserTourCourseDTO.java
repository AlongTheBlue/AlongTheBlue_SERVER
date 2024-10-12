package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserTourCourseDTO {
    private String title;
    private String writing;
//    private List<TourPostHashTag> tags;
    private String imgUrl;
    private List<TourPostItemDTO> postItems;
}
