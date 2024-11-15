package org.alongtheblue.alongtheblue_server.domain.alongBlues.dto.request;

import java.util.List;

public record CreateBlueCourseRequestDto(
        String title,
        List<CreateBlueItemRequestDto> blueItems
) {
    public CreateBlueCourseServiceRequestDto toServiceRequest() {
        return new CreateBlueCourseServiceRequestDto(title, blueItems);
    }
}
