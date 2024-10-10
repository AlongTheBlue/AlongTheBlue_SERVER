package org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.request;

import java.util.List;

public record UserTourCourseRequestDto(
        String title,
        String writing,
        List<TourPostItemRequestDto> tourItems,
//        List<TourPostHashTag> hashTags,
        List<List<Integer>> imgIndexArr
) {
}
