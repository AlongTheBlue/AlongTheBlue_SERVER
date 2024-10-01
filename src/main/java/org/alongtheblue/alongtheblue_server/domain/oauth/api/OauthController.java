package org.alongtheblue.alongtheblue_server.domain.oauth.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.oauth.application.KakaoService;
import org.alongtheblue.alongtheblue_server.domain.userInfo.application.UserInfoService;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "회원가입/로그인 API", description = "회원가입, 로그인, 사용자 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {
    private final KakaoService kakaoService;
    private final UserInfoService userinfoService;

    @Operation(summary="리다이렉션 url 반환 API")
    @GetMapping("/login")
    public ApiResponse<String> getKakaoLoginUrl() {
        return kakaoService.getKakaoLoginUrl();
    }

    @Operation(summary="로그인 후 콜백 메소드")
    @GetMapping("/callback")
    public ApiResponse<String> kakaoLogin(@RequestParam("code") String code) {
        System.out.println("0000000000000000000000000");
        String accessToken = kakaoService.getAccessToken(code);
        Map<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
        return ApiResponse.ok("사용자의 user ID를 성공적으로 조회했습니다.", userinfoService.retrieveOrCreateUser(userInfo).getData().getUid());
    }
}
