package org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.response;

import java.util.List;

public record TourPostItemResponseDto(
        String title,
        String address,
        String xMap,
        String yMap,
        String comment,
        String category,
        String contentId,
        List<TourImageResponseDto> tourImages
) {
}