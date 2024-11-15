package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dto.response;

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