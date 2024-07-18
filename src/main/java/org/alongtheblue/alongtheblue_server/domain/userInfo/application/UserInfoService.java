package org.alongtheblue.alongtheblue_server.domain.userInfo.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.userInfo.dao.UserInfoRepository;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.CreateUserInfoServiceDto;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    public ApiResponse<UserInfo> createUserInfo(CreateUserInfoServiceDto dto) {
        UserInfo userInfo = dto.toEntity();
        UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        return ApiResponse.ok("사용자 정보를 성공적으로 등록하였습니다", savedUserInfo);
    }

    public ApiResponse<List<UserInfo>> retrieveAllUserInfo() {
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        if(userInfoList.isEmpty()) {
            return ApiResponse.ok("UserInfo가 존재하지 않습니다.");
        }
        return ApiResponse.ok("UserInfo 목록을 성공적으로 조회했습니다.", userInfoList);
    }
}
