package org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.request;

public record TourPostItemRequestDto(
        String title,
        String category,
        String address,
        String x,
        String y,
        String comment
//        String contentsId
) {

}