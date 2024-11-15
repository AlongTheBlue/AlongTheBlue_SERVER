package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dto.request;

import org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain.UserTourCourse;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain.TourPostItem;

import java.util.Date;
import java.util.List;

public record CreateUserTourCourseServiceRequestDto(
        String title,
        String content,
        List<TourPostItemRequestDto> tourPostItems,
//        List<TourPostHashTag> hashTags,
        List<List<Integer>> imgIndexArr
) {
    public UserTourCourse toEntity(UserInfo userInfo, Date date, List<TourPostItem> tourItems) {
        return UserTourCourse.builder()
                .createdate(date)
                .title(title)
                .writing(content)
                .tourPostItems(tourItems)
                .userInfo(userInfo)
                .build();
    }
}
