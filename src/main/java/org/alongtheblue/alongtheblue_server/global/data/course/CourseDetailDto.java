package org.alongtheblue.alongtheblue_server.global.data.course;

import org.alongtheblue.alongtheblue_server.global.data.courseHashTag.CourseHashTag;
import org.alongtheblue.alongtheblue_server.global.data.courseItem.CourseItemResponseDto;

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