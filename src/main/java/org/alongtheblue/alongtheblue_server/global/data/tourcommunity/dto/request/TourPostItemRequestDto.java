package org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.request;

public record TourPostItemRequestDto(
        String title,
        String category,
        String address,
        String xMap,
        String yMap,
        String comment
//        String contentsId
) {

}