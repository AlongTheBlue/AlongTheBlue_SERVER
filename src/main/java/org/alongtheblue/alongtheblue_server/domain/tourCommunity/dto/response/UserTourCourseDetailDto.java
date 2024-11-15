package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dto.response;

import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.UserInfoDto;

import java.util.List;

public record UserTourCourseDetailDto(
        UserInfoDto user,
        String title,
        String introduction,
        List<TourPostItemResponseDto> travelCourses
) {

}