package org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.request;

import org.alongtheblue.alongtheblue_server.global.data.alongBlues.BlueItem;

public record CreateBlueItemRequestDto(
        String title,
        String address,
        String xMap,
        String yMap,
        String category
) {
    public BlueItem toEntity() {
        return BlueItem.builder()
                .name(title)
                .address(address)
                .xMap(xMap)
                .yMap(yMap)
                .category(category)
                .build();
    }
}
