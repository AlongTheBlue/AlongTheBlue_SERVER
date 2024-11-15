package org.alongtheblue.alongtheblue_server.domain.search.dto;

public record AllCategoryResponseDto(
        String contentid,
        String title,
        String address,
        String xMap,
        String yMap,
        String category
) {

}
