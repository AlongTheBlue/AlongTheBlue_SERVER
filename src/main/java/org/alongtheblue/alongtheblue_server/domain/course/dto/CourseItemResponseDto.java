package org.alongtheblue.alongtheblue_server.domain.course.dto;

import java.util.List;

public record CourseItemResponseDto(
        String title,
        String address,
        String introduction,
        String category,
        String xMap,
        String yMap,
        List<CourseImageDto> images
) {
}