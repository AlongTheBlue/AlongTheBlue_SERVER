package org.alongtheblue.alongtheblue_server.global.data.search;

import jdk.jfr.Category;

public record AllCategoryResponseDto(
        String contentid,
        String title,
        String address,
        String xMap,
        String yMap,
        String category
) {

}
