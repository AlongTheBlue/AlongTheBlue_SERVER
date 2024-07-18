package org.alongtheblue.alongtheblue_server.domain.userInfo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.userInfo.application.UserInfoService;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.CreateUserInfoDto;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UserInfo API", description = "UserInfo 등록 / 수정 / 삭제 / 조회")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Operation(summary = "UserInfo 등록 API")
    @PostMapping("/userInfo")
    public ApiResponse<UserInfo> createUserInfo(@Valid @RequestBody CreateUserInfoDto dto) {
        return userInfoService.createUserInfo(dto.toServiceFaq());
    }
    @Operation(summary = "UserInfo 전체 조회 API")
    @GetMapping("/userInfo")
    public ApiResponse<List<UserInfo>> retrieveAllFaq() {
        return userInfoService.retrieveAllUserInfo();
    }

}
