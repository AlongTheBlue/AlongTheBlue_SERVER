package org.alongtheblue.alongtheblue_server.global.data.course;

public record CourseHomeDto(
        Long contentid,
        String subtitle,
        String title,
        String img,
        String hashTag
) {
}