package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dto;

import lombok.Getter;
import lombok.Setter;
import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.UserInfoDto;

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
