package org.alongtheblue.alongtheblue_server.domain.blue.dto;

public record RecommendBlueDto(
        Long id,
        String title,
        String xMap,
        String yMap,
        String address,
        String city,
        String category,
        String image
) {
}
