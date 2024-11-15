package org.alongtheblue.alongtheblue_server.domain.course.dto;

public record CourseHomeDto(
        Long contentid,
        String subtitle,
        String title,
        String img,
        String hashTag
) {
}