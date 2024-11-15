package org.alongtheblue.alongtheblue_server.domain.alongBlues.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.UserInfoDto;

public record BlueCourseResponseDto(
        UserInfoDto user,
        Long id,
        String title
) {

}