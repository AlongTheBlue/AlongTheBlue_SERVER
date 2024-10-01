package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import java.util.List;

public record TourCourseRequestDto(
        String title,
        String writing,
        List<TourPostItem> tourItems,
//        List<TourPostHashTag> hashTags,
        List<List<Integer>> index
) {
}
