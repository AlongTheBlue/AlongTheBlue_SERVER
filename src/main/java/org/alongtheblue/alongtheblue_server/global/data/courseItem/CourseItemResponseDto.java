package org.alongtheblue.alongtheblue_server.global.data.courseItem;

import org.alongtheblue.alongtheblue_server.global.data.courseHashTag.CourseHashTag;
import org.alongtheblue.alongtheblue_server.global.data.courseImage.CourseImage;
import org.alongtheblue.alongtheblue_server.global.data.courseImage.CourseImageDto;

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