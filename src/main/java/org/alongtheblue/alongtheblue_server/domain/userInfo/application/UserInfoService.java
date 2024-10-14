package org.alongtheblue.alongtheblue_server.domain.userInfo.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.userInfo.dao.UserInfoRepository;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.CreateUserInfoServiceDto;
import org.alongtheblue.alongtheblue_server.domain.userInfo.dto.UserInfoDto;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.error.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public ApiResponse<UserInfo> retrieveOrCreateUser(Map<String, Object> userInfo) {
        String uuid = userInfo.get("id").toString();
        Optional<UserInfo> optionalUser = userInfoRepository.findByUid(uuid);
        if(optionalUser.isEmpty()) {
            System.out.println("11111111111111");
            return this.createUser(userInfo);
        }
        UserInfo savedUser = optionalUser.get();
        return ApiResponse.ok("사용자 정보를 성공적으로 조회했습니다.", savedUser);
//        User user = userRepository.findById(uuid).orElseGet(() -> {
//            User newUser = new User();
//            newUser.setId(uuid);
//            newUser.setEmail(email);
//            newUser.setNickname(title);
//            return userRepository.save(newUser);
//        });
    }

    private ApiResponse<UserInfo> createUser(Map<String, Object> userInfo) {
        Map<String, Object> kakao_account = (Map<String, Object>) userInfo.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");
        String uuid = userInfo.get("id").toString();
        String profile_image_url = (String) profile.get("profile_image_url");
        String name = (String) profile.get("nickname");
        System.out.println(name);
        UserInfo user = UserInfo.builder()
                .uid(uuid)
                .userName(name)
                .profileImageUrl(profile_image_url)
                .build();
        UserInfo savedUser = userInfoRepository.save(user);
        return ApiResponse.ok("사용자 정보를 성공적으로 등록했습니다.", savedUser);
    }

    public ApiResponse<UserInfo> retrieveUserInfo(String uid) {
        Optional<UserInfo> optionalUser = userInfoRepository.findByUid(uid);
        if(optionalUser.isEmpty()) return ApiResponse.withError(ErrorCode.INVALID_USER_ID);
        UserInfo userInfo = optionalUser.get();
        return ApiResponse.ok("사용자 정보를 성공적으로 조회했습니다.", userInfo);
    }

    public ApiResponse<UserInfoDto> retrieveUserInfoDto(String uid) {
        UserInfo userInfo = retrieveUserInfo(uid).getData();
        UserInfoDto userInfoDto = new UserInfoDto(
                userInfo.getUid(),
                userInfo.getUserName(),
                userInfo.getProfileImageUrl()
        );
        return  ApiResponse.ok("사용자 정보를 성공적으로 조회했습니다.", userInfoDto);
    }
}
