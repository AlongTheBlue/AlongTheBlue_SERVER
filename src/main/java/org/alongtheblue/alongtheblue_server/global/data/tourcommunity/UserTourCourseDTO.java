package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import lombok.Getter;
import lombok.Setter;
import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.UserInfoDto;
import org.alongtheblue.alongtheblue_server.global.data.course.Course;

import java.util.List;
@Getter
@Setter
public class UserTourCourseDTO {
    private UserInfoDto user;
    private Long id;
    private String title;
    private String content;
//    private List<TourPostHashTag> tags;
    private String imgUrl;
//    private List<TourPostItemDTO> postItems;
}
