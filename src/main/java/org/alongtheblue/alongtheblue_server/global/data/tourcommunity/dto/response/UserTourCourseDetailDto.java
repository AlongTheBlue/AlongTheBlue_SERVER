package org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.response;

import java.util.List;

public record UserTourCourseDetailDto(
        String title,
        String content,
        List<TourPostItemResponseDto> tourItems
//        List<TourPostHashTag> hashTags,
) {
}