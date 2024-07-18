package org.alongtheblue.alongtheblue_server.domain.userInfo.dto;

import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;

public record CreateUserInfoServiceDto(
        String userName,
        String uId
) {

    public CreateUserInfoServiceDto(String userName, String uId) {
        this.userName = userName;
        this.uId = uId;
    }

    public UserInfo toEntity() {
        return UserInfo.builder()
                .userName(userName)
                .uid(uId)
                .build();
    }
}
