package org.alongtheblue.alongtheblue_server.domain.global.dto.response;

import java.util.List;

public record DetailResponseDto(
        String contentid,
        String title,
        String address,
        String time,
        String weatherCondition,
        String temperature,
        String infoCenter,
        String introduction,
        String img,
        String xMap,
        String yMap
) {

}
