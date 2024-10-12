package org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.request;

import org.alongtheblue.alongtheblue_server.global.data.alongBlues.BlueItem;

import java.util.Date;
import java.util.List;

public record CreateBlueCourseRequestDto(
        String title,
        List<CreateBlueItemRequestDto> blueItems
) {
    public CreateBlueCourseServiceRequestDto toServiceRequest() {
        return new CreateBlueCourseServiceRequestDto(title, blueItems);
    }
}
