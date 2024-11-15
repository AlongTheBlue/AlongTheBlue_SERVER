package org.alongtheblue.alongtheblue_server.domain.alongBlues.dto.request;

import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.alongtheblue.alongtheblue_server.domain.alongBlues.domain.BlueCourse;
import org.alongtheblue.alongtheblue_server.domain.alongBlues.domain.BlueItem;

import java.util.Date;
import java.util.List;

public record CreateBlueCourseServiceRequestDto(
        String title,
        List<CreateBlueItemRequestDto> blueItems
) {
    public BlueCourse toEntity(Date createDay, List<BlueItem> itemList, UserInfo userInfo) {
        BlueCourse course = BlueCourse.builder()
                .title(title)
                .createDay(createDay)
                .blueItems(itemList)
                .userInfo(userInfo)
                .build();

        // 각 BlueItem에 BlueCourse 설정
        for (BlueItem item : itemList) {
            item.setBlueCourse(course);
        }

        return course;
    }
}
