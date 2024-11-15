package org.alongtheblue.alongtheblue_server.domain.course.dto;

import org.alongtheblue.alongtheblue_server.domain.course.domain.CourseHashTag;

import java.util.List;

public record CourseDetailDto(
        Long contentid,
        String subtitle,
        String title,
        String introduction,
        List<CourseHashTag> hashtags,
        List<CourseItemResponseDto> travelCourses
) {
}