package org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.response;

import org.alongtheblue.alongtheblue_server.global.data.courseImage.CourseImageDto;

import java.util.List;

public record TourPostItemResponseDto(
        String title,
        String address,
        String introduction,
        String category,
        String xMap,
        String yMap,
        List<TourImageResponseDto> images
) {

}