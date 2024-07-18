package org.alongtheblue.alongtheblue_server.domain.userInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateUserInfoDto(
        @Schema(description = "사용자 정보, 빈 값/공백/null 을 허용하지 않습니다.")
        @NotBlank(message = "사용자 정보는 필수 값입니다.")
        String userName,
        @Schema(description = "사용자 소셜아이디, 빈 값/공백/null 을 허용하지 않습니다.")
        @NotBlank(message = "사용자 소셜아이디는 필수 값입니다.")
        String uId
) {
    public CreateUserInfoServiceDto toServiceFaq() {
        return new CreateUserInfoServiceDto(userName, uId);
    }
}
