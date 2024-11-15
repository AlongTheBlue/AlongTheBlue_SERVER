package org.alongtheblue.alongtheblue_server.domain.restaurant.dto.response;

public record RestaurantResponseDto(
        String address,
        String title,
        String contentid,
        String img,
        String xMap,
        String yMap
) {

}
