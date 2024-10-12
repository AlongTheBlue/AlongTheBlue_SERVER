package org.alongtheblue.alongtheblue_server.global.data.search;

public record SearchResponseDto(
        String contentid,
        String title,
        String address,
        String xMap,
        String yMap,
        String category
) {

}
