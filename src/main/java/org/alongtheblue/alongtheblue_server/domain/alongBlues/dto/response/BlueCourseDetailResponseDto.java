package org.alongtheblue.alongtheblue_server.domain.alongBlues.dto.response;

import java.util.List;

public record BlueCourseDetailResponseDto(
        String title,
        List<BlueItemResponseDto> blueItems
) {

}
