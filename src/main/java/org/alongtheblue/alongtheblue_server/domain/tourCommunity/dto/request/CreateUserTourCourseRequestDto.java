package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dto.request;

import java.util.List;

public record CreateUserTourCourseRequestDto(
        String title,
        String content,
        List<TourPostItemRequestDto> tourPostItems,
//        List<TourPostHashTag> hashTags,
        List<List<Integer>> imgIndexArr
) {
    public CreateUserTourCourseServiceRequestDto toServiceRequest() {
        return new CreateUserTourCourseServiceRequestDto(title, content, tourPostItems, imgIndexArr);
    }
}
