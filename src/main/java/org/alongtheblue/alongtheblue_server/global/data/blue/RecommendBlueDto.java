package org.alongtheblue.alongtheblue_server.global.data.blue;

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
