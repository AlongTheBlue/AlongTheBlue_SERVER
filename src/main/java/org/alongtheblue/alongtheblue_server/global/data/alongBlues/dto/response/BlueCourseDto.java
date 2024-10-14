package org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.response;

import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.UserInfoDto;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.response.TourPostItemResponseDto;

import java.util.List;

public record BlueCourseDto(
        UserInfoDto user,
        String title,
        List<BlueItemResponseDto> travelCourses
) {
}

