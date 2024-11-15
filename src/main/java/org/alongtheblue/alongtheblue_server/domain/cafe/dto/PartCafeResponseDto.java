package org.alongtheblue.alongtheblue_server.domain.cafe.dto;

public record PartCafeResponseDto(
        String address,
        String title,
        String contentid,
        String img,
        String xMap,
        String yMap,
        String category
) {

}
