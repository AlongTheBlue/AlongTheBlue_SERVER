package org.alongtheblue.alongtheblue_server.domain.alongBlues.dto.response;

import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.UserInfoDto;

import java.util.List;

public record BlueCourseDto(
        UserInfoDto user,
        String title,
        List<BlueItemResponseDto> travelCourses
) {
}

